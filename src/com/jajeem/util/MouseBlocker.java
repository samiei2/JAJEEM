package com.jajeem.util;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;
import com.sun.jna.platform.win32.WinUser.MSG;

public class MouseBlocker {
	User32 USER32INST;

	private Thread mouseHookThread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				if (!isHooked) {
					hhk = USER32INST.SetWindowsHookEx(User32.WH_MOUSE_LL,
							hookTheMouse(),
							Kernel32.INSTANCE.GetModuleHandle(null), 0);

					isHooked = true;

					System.out.println("Mouse hook is set. Click anywhere.");
					// message dispatch loop (message pump)
					MSG msg = new MSG();
					while ((USER32INST.GetMessage(msg, null, 0, 0)) != 0) {
						USER32INST.TranslateMessage(msg);
						USER32INST.DispatchMessage(msg);
						if (!isHooked)
							break;
					}
				} else
					System.out.println("The Hook is already installed.");
			} catch (Exception e) {
				System.err.println("Caught exception in MouseHook!");
			}
		}
	});

	public MouseBlocker() {
		if (!Platform.isWindows()) {
			throw new UnsupportedOperationException(
					"Not supported on this platform.");
		}
		USER32INST = User32.INSTANCE;
		hookTheMouse();
		Native.setProtected(true);
		USER32INST = User32.INSTANCE;
		Native.setProtected(true);
	}

	private HHOOK hhk;
	private boolean isHooked = false;

	public static final int WM_LBUTTONDOWN = 513;
	public static final int WM_LBUTTONUP = 514;
	public static final int WM_RBUTTONDOWN = 516;
	public static final int WM_RBUTTONUP = 517;
	public static final int WM_MBUTTONDOWN = 519;
	public static final int WM_MBUTTONUP = 520;

	public void unsetMouseHook() {
		USER32INST.UnhookWindowsHookEx(hhk);
		isHooked = false;
		System.out.println("Mouse hook is unset.");
	}

	public boolean isIsHooked() {
		return isHooked;
	}

	public void start() {
		mouseHookThread.start();
	}
	
	public void stop() {
		mouseHookThread.interrupt();
	}

	private interface LowLevelMouseProc extends HOOKPROC {
		LRESULT callback(int nCode, WPARAM wParam, LPARAM lParam);
	}

	private LowLevelMouseProc hookTheMouse() {
		return new LowLevelMouseProc() {
			@Override
			public LRESULT callback(int nCode, WPARAM wParam, LPARAM info) {
				if (nCode >= 0) {
					switch (wParam.intValue()) {
					case MouseBlocker.WM_LBUTTONDOWN:
						break;
					case MouseBlocker.WM_RBUTTONDOWN:
						break;
					case MouseBlocker.WM_MBUTTONDOWN:
						break;
					default:
						break;
					}
				}
				return new LRESULT(1);
			}
		};
	}

}