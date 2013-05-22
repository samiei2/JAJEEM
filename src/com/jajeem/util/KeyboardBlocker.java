/* Copyright (c) 2007 Timothy Wall, All Rights Reserved

 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.  
 */
package com.jajeem.util;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

/** Sample implementation of a low-level keyboard hook on W32. */
public class KeyboardBlocker {
	private static volatile boolean quit;
	private static HHOOK hhk;
	private static LowLevelKeyboardProc keyboardHook;
	final static User32 lib = User32.INSTANCE;
	private static Thread keyboardHookThread = new Thread(new Runnable() {

		@Override
		public void run() {
			while (!quit) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
				}
			}
			System.err.println("unhook and exit");
			lib.UnhookWindowsHookEx(hhk);
		}

	});

	public void start() {

		HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
		keyboardHook = new LowLevelKeyboardProc() {
			public LRESULT callback(int nCode, WPARAM wParam,
					KBDLLHOOKSTRUCT info) {
				if (nCode >= 0) {
					switch (wParam.intValue()) {
					case WinUser.WM_KEYUP:
					case WinUser.WM_KEYDOWN:
					case WinUser.WM_SYSKEYUP:
					case WinUser.WM_SYSKEYDOWN:
						System.err.println("in callback, key=" + info.vkCode);
						if (info.vkCode == 81) {
							quit = true;
						} else {
							return new LRESULT(1);
						}
					}
				}
				return lib
						.CallNextHookEx(hhk, nCode, wParam, info.getPointer());
			}
		};
		hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod,
				0);
		System.out
				.println("Keyboard hook installed, type anywhere, 'q' to quit");
		keyboardHookThread.start();

		// This bit never returns from GetMessage
		int result;
		MSG msg = new MSG();
		while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
			if (result == -1) {
				System.err.println("error in get message");
				break;
			} else {
				System.err.println("got message");
				lib.TranslateMessage(msg);
				lib.DispatchMessage(msg);
			}
		}
		lib.UnhookWindowsHookEx(hhk);
	}

	public void stop() {
		keyboardHookThread.interrupt();
		lib.UnhookWindowsHookEx(hhk);
	}
}