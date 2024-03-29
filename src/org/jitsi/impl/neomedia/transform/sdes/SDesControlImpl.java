/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.neomedia.transform.sdes;

import gnu.java.zrtp.utils.ZrtpFortuna;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.jitsi.impl.neomedia.AbstractRTPConnector;
import org.jitsi.impl.neomedia.transform.TransformEngine;
import org.jitsi.impl.neomedia.transform.zrtp.SecurityEventManager;
import org.jitsi.service.neomedia.MediaType;
import org.jitsi.service.neomedia.SDesControl;
import org.jitsi.service.neomedia.SrtpControl;
import org.jitsi.service.neomedia.event.SrtpListener;

import ch.imvs.sdes4j.srtp.SrtpCryptoAttribute;
import ch.imvs.sdes4j.srtp.SrtpCryptoSuite;
import ch.imvs.sdes4j.srtp.SrtpSDesFactory;

/**
 * Default implementation of {@link SDesControl} that supports the crypto suites
 * of the original RFC4568 and the KDR parameter, but nothing else.
 * 
 * @author Ingo Bauersachs
 */
public class SDesControlImpl implements SDesControl {
	/**
	 * List of enabled crypto suites.
	 */
	private List<String> enabledCryptoSuites = new ArrayList<String>(3) {
		private static final long serialVersionUID = 0L;

		{
			add(SrtpCryptoSuite.AES_CM_128_HMAC_SHA1_80);
			add(SrtpCryptoSuite.AES_CM_128_HMAC_SHA1_32);
			add(SrtpCryptoSuite.F8_128_HMAC_SHA1_80);
		}
	};

	/**
	 * List of supported crypto suites.
	 */
	private final List<String> supportedCryptoSuites = new ArrayList<String>(3) {
		private static final long serialVersionUID = 0L;

		{
			add(SrtpCryptoSuite.AES_CM_128_HMAC_SHA1_80);
			add(SrtpCryptoSuite.AES_CM_128_HMAC_SHA1_32);
			add(SrtpCryptoSuite.F8_128_HMAC_SHA1_80);
		}
	};

	private SrtpSDesFactory sdesFactory;
	private SrtpCryptoAttribute[] attributes;
	private SDesTransformEngine engine;
	private SrtpCryptoAttribute selectedInAttribute;
	private SrtpCryptoAttribute selectedOutAttribute;
	private SrtpListener srtpListener;

	/**
	 * SDESControl
	 */
	public SDesControlImpl() {
		sdesFactory = new SrtpSDesFactory();
		Random r = new Random() {
			private static final long serialVersionUID = 0L;

			@Override
			public void nextBytes(byte[] bytes) {
				ZrtpFortuna.getInstance().getFortuna().nextBytes(bytes);
			}
		};
		sdesFactory.setRandomGenerator(r);
	}

	@Override
	public void setEnabledCiphers(Iterable<String> ciphers) {
		enabledCryptoSuites.clear();
		for (String c : ciphers) {
			enabledCryptoSuites.add(c);
		}
	}

	@Override
	public Iterable<String> getSupportedCryptoSuites() {
		return Collections.unmodifiableList(supportedCryptoSuites);
	}

	@Override
	public void cleanup() {
		if (engine != null) {
			engine.close();
			engine = null;
		}
	}

	@Override
	public void setSrtpListener(SrtpListener srtpListener) {
		this.srtpListener = srtpListener;
	}

	@Override
	public SrtpListener getSrtpListener() {
		return srtpListener;
	}

	@Override
	public boolean getSecureCommunicationStatus() {
		return engine != null;
	}

	/**
	 * Not used.
	 * 
	 * @param masterSession
	 *            not used.
	 */
	@Override
	public void setMasterSession(boolean masterSession) {
	}

	@Override
	public void start(MediaType type) {
		// in srtp the started and security event is one after another
		// in some other security mechanisms (e.g. zrtp) there can be started
		// and no security one or security timeout event
		srtpListener
				.securityNegotiationStarted(
						type.equals(MediaType.AUDIO) ? SecurityEventManager.AUDIO_SESSION
								: SecurityEventManager.VIDEO_SESSION, this);

		srtpListener
				.securityTurnedOn(
						type.equals(MediaType.AUDIO) ? SecurityEventManager.AUDIO_SESSION
								: SecurityEventManager.VIDEO_SESSION,
						selectedInAttribute.getCryptoSuite().encode(), this);
	}

	@Override
	public void setMultistream(SrtpControl master) {
	}

	@Override
	public TransformEngine getTransformEngine() {
		if (engine == null) {
			engine = new SDesTransformEngine(selectedInAttribute,
					selectedOutAttribute);
		}
		return engine;
	}

	/**
	 * Initializes the available SRTP crypto attributes containing: he
	 * crypto-suite, the key-param and the session-param.
	 */
	private void initAttributes() {
		if (attributes == null) {
			attributes = new SrtpCryptoAttribute[enabledCryptoSuites.size()];
			for (int i = 0; i < attributes.length; i++) {
				attributes[i] = sdesFactory.createCryptoAttribute(i + 1,
						enabledCryptoSuites.get(i));
			}
		}
	}

	/**
	 * Returns the crypto attributes enabled on this computer.
	 * 
	 * @return The crypto attributes enabled on this computer.
	 */
	@Override
	public SrtpCryptoAttribute[] getInitiatorCryptoAttributes() {
		initAttributes();

		return attributes;
	}

	/**
	 * Chooses a supported crypto attribute from the peer's list of supplied
	 * attributes and creates the local crypto attribute. Used when the control
	 * is running in the role as responder.
	 * 
	 * @param peerAttributes
	 *            The peer's crypto attribute offering.
	 * 
	 * @return The local crypto attribute for the answer of the offer or null if
	 *         no matching cipher suite could be found.
	 */
	@Override
	public SrtpCryptoAttribute responderSelectAttribute(
			Iterable<SrtpCryptoAttribute> peerAttributes) {
		for (SrtpCryptoAttribute ea : peerAttributes) {
			for (String suite : enabledCryptoSuites) {
				if (suite.equals(ea.getCryptoSuite().encode())) {
					selectedInAttribute = ea;
					selectedOutAttribute = sdesFactory.createCryptoAttribute(1,
							suite);
					return selectedOutAttribute;
				}
			}
		}
		return null;
	}

	/**
	 * Select the local crypto attribute from the initial offering (@see
	 * {@link #getInitiatorCryptoAttributes()}) based on the peer's first
	 * matching cipher suite.
	 * 
	 * @param peerAttributes
	 *            The peer's crypto offers.
	 * 
	 * @return A SrtpCryptoAttribute when a matching cipher suite was found.
	 *         Null otherwise.
	 */
	@Override
	public SrtpCryptoAttribute initiatorSelectAttribute(
			Iterable<SrtpCryptoAttribute> peerAttributes) {
		for (SrtpCryptoAttribute peerCA : peerAttributes) {
			for (SrtpCryptoAttribute localCA : attributes) {
				if (localCA.getCryptoSuite().equals(peerCA.getCryptoSuite())) {
					selectedInAttribute = peerCA;
					selectedOutAttribute = localCA;
					return peerCA;
				}
			}
		}
		return null;
	}

	@Override
	public SrtpCryptoAttribute getInAttribute() {
		return selectedInAttribute;
	}

	@Override
	public SrtpCryptoAttribute getOutAttribute() {
		return selectedOutAttribute;
	}

	@Override
	public void setConnector(AbstractRTPConnector newValue) {
	}

	/**
	 * Returns true, SDES always requires the secure transport of its keys.
	 * 
	 * @return true
	 */
	@Override
	public boolean requiresSecureSignalingTransport() {
		return true;
	}
}
