package com.jajeem.util;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.HOOKPROC;
import com.sun.jna.platform.win32.WinUser.MSG;

/** Sample implementation of a low-level keyboard hook on W32. */
// source:
// https://github.com/thepaul/libjna-java/blob/master/contrib/w32keyhook/KeyHook.java

public class MouseHook implements Runnable {
	private static HHOOK hhk;
	private static LowLevelMouseProc mouseHook;
	private static boolean ignoreCallback;
	protected Thread thread;

	public static final int WM_LBUTTONDOWN = 513;
	public static final int WM_LBUTTONUP = 514;
	public static final int WM_RBUTTONDOWN = 516;
	public static final int WM_RBUTTONUP = 517;
	public static final int WM_MBUTTONDOWN = 519;
	public static final int WM_MBUTTONUP = 520;

	public MouseHook() {
		thread = new Thread(this);
	}

	public void start() {
		thread.start();
	}

	public boolean isIgnoreCallback() {
		return ignoreCallback;
	}

	public void setIgnoreCallback(boolean ignoreCallback) {
		MouseHook.ignoreCallback = ignoreCallback;
	}

	private interface LowLevelMouseProc extends HOOKPROC {
		LRESULT callback(int nCode, WPARAM wParam, LPARAM lParam);
	}

	@Override
	public void run() {
		final User32 lib = User32.INSTANCE;
		HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);

		mouseHook = new LowLevelMouseProc() {
			@Override
			public LRESULT callback(int nCode, WPARAM wParam, LPARAM lParam) {
				if (isIgnoreCallback()) {
					return new LRESULT(1);
				} else {
					return lib.CallNextHookEx(hhk, nCode, wParam,
							lParam.toPointer());
				}
			}
		};

		hhk = lib.SetWindowsHookEx(WinUser.WH_MOUSE_LL, mouseHook, hMod, 0);

		MSG msg = new MSG();
		lib.GetMessage(msg, null, 0, 0);
	}

}