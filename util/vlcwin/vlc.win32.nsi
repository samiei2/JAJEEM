;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; NSIS installer script for vlc ;
; (http://nsis.sourceforge.net) ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

!include "languages\declaration.nsh"

!define PRODUCT_NAME "VLC media player"
!define VERSION 2.1.1
!define PRODUCT_VERSION 2.1.1
!define PRODUCT_GROUP "VideoLAN"
!define PRODUCT_PUBLISHER "VideoLAN"
!define PRODUCT_WEB_SITE "http://www.videolan.org/"
!define PRODUCT_DIR_REGKEY "Software\VideoLAN\VLC"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"
!define PRODUCT_ID "{ea92ef52-afe4-4212-bacb-dfe9fca94cd6}"

!define MUI_LANGDLL_REGISTRY_ROOT "HKLM"
!define MUI_LANGDLL_REGISTRY_KEY "${PRODUCT_DIR_REGKEY}"
!define MUI_LANGDLL_REGISTRY_VALUENAME "Language"

!define MEMENTO_REGISTRY_ROOT ${PRODUCT_UNINST_ROOT_KEY}
!define MEMENTO_REGISTRY_KEY "${PRODUCT_UNINST_KEY}"

!define INSTALL_ACTIVEX
!define INSTALL_MOZILLA

Var ReinstallType
Var ReinstallUninstallBtn
Var PerformUpdate
Var PreviousVersion
Var PreviousVersionState
Var PreviousInstallDir
Var UninstallLog

;;;;;;;;;;;;;;;;;;;;;;;;;
; General configuration ;
;;;;;;;;;;;;;;;;;;;;;;;;;

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
 OutFile ..\vlc-${VERSION}-win32.exe
#  OutFile ..\vlc-${VERSION}-win64.exe
InstallDir "$PROGRAMFILES\VideoLAN\VLC"
!ifdef NSIS_LZMA_COMPRESS_WHOLE
SetCompressor lzma
!else
SetCompressor /SOLID lzma
!endif

SetOverwrite ifnewer
CRCCheck on
BrandingText "${PRODUCT_GROUP} ${PRODUCT_NAME}"

InstType $Name_InstTypeRecommended
InstType $Name_InstTypeMinimum
InstType $Name_InstTypeFull

RequestExecutionLevel user
!addincludedir NSIS
!addplugindir NSIS
!include UAC.nsh
!include WinVer.nsh
!include FileFunc.nsh
!include MUI2.nsh
!include Memento.nsh

!insertmacro GetParameters
!insertmacro GetOptions

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; NSIS Modern User Interface configuration ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; MUI Settings
  !define MUI_ABORTWARNING
  !define MUI_ICON "vlc.ico"
  !define MUI_UNICON "vlc.ico"
  !define MUI_COMPONENTSPAGE_SMALLDESC

; Installer pages
  ; Welcome page
    !define MUI_PAGE_CUSTOMFUNCTION_PRE PageFastUpdatePre
    !define MUI_WELCOMEPAGE_TITLE_3LINES
    !insertmacro MUI_PAGE_WELCOME
  ; Optional update page
    Page custom PageReinstall PageLeaveReinstall
  ; License page
    !define MUI_LICENSEPAGE_BUTTON $(^NextBtn)
    !define MUI_LICENSEPAGE_TEXT_BOTTOM $License_NextText
    !define MUI_PAGE_CUSTOMFUNCTION_PRE PageSkipPre
    !insertmacro MUI_PAGE_LICENSE "COPYING.txt"
  ; Components page
    !define MUI_PAGE_CUSTOMFUNCTION_PRE PageComponentsPre
    !insertmacro MUI_PAGE_COMPONENTS
  ; Directory page
    !define MUI_PAGE_CUSTOMFUNCTION_PRE PageDirectoryPre
    !insertmacro MUI_PAGE_DIRECTORY
  ; Instfiles page
    !insertmacro MUI_PAGE_INSTFILES
  ; Finish page
    !define MUI_PAGE_CUSTOMFUNCTION_PRE PageFastUpdatePre
    !define MUI_FINISHPAGE_RUN
    !define MUI_FINISHPAGE_RUN_FUNCTION AppExecAs
    !define MUI_FINISHPAGE_LINK $Link_VisitWebsite
    !define MUI_FINISHPAGE_LINK_LOCATION "http://www.videolan.org/vlc/"
    !define MUI_FINISHPAGE_NOREBOOTSUPPORT
    !insertmacro MUI_PAGE_FINISH

; Uninstaller pages
    !insertmacro MUI_UNPAGE_CONFIRM
    !insertmacro MUI_UNPAGE_COMPONENTS
    !insertmacro MUI_UNPAGE_INSTFILES
    !insertmacro MUI_UNPAGE_FINISH

; Language files
  !insertmacro MUI_LANGUAGE "English" # first language is the default language
  !insertmacro MUI_LANGUAGE "French"
  !insertmacro MUI_LANGUAGE "German"
  !insertmacro MUI_LANGUAGE "Spanish"
  !insertmacro MUI_LANGUAGE "SimpChinese"
  !insertmacro MUI_LANGUAGE "TradChinese"
  !insertmacro MUI_LANGUAGE "Japanese"
  !insertmacro MUI_LANGUAGE "Korean"
  !insertmacro MUI_LANGUAGE "Italian"
  !insertmacro MUI_LANGUAGE "Dutch"
  !insertmacro MUI_LANGUAGE "Danish"
  !insertmacro MUI_LANGUAGE "Swedish"
  !insertmacro MUI_LANGUAGE "Norwegian"
  !insertmacro MUI_LANGUAGE "Finnish"
  !insertmacro MUI_LANGUAGE "Greek"
  !insertmacro MUI_LANGUAGE "Russian"
  !insertmacro MUI_LANGUAGE "Portuguese"
  !insertmacro MUI_LANGUAGE "PortugueseBR"
  !insertmacro MUI_LANGUAGE "Arabic"
  !insertmacro MUI_LANGUAGE "Polish"
  !insertmacro MUI_LANGUAGE "Romanian"
  !insertmacro MUI_LANGUAGE "Slovak"
  !insertmacro MUI_LANGUAGE "Serbian"
  !insertmacro MUI_LANGUAGE "Czech"
  !insertmacro MUI_LANGUAGE "Hungarian"
  !insertmacro MUI_LANGUAGE "Catalan"
  !insertmacro MUI_LANGUAGE "Bulgarian"
  !insertmacro MUI_LANGUAGE "Estonian"
  !insertmacro MUI_LANGUAGE "Lithuanian"
  !insertmacro MUI_LANGUAGE "Basque"

; Reserve files for solid compression
  !insertmacro MUI_RESERVEFILE_LANGDLL

;;;;;;;;;;;;;;;;;;;;;;;
; Macro and Functions ;
;;;;;;;;;;;;;;;;;;;;;;;

!include helpers/extensions.nsh
!include helpers/install.nsh
!include helpers/utils.nsh

;;;;;;;;;;;;;;;;;;;;;;
; Installer sections ;
; The CORE of the    ;
; installer          ;
;;;;;;;;;;;;;;;;;;;;;;

${MementoSection} $Name_Section01 SEC01
  SectionIn 1 2 3 RO
  SetShellVarContext all
  SetOutPath "$INSTDIR"

  !insertmacro OpenUninstallLog

  ; VLC.exe, libvlc.dll
  !insertmacro InstallFile vlc.exe
  !insertmacro InstallFile vlc.exe.manifest
  !insertmacro InstallFile vlc-cache-gen.exe

  ; All dlls
  !insertmacro InstallFile *.dll

  ; Text files
  !insertmacro InstallFile *.txt

  ; Subfolders
  !insertmacro InstallFolder plugins
  !insertmacro InstallFolder locale
  !insertmacro InstallFolder sdk
   !insertmacro InstallFolder skins
     !insertmacro InstallFolder lua

  ; Generate the cache and add it to uninstall.log
  nsExec::ExecToStack "$INSTDIR\vlc-cache-gen.exe $INSTDIR\plugins"
  FindFirst $0 $1 "$INSTDIR\plugins\*.dat"
  FileWrite $UninstallLog "plugins\$1$\r$\n"
  FindClose $0


  ; URLs
  WriteIniStr "$INSTDIR\${PRODUCT_GROUP} Website.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}"
  FileWrite $UninstallLog "${PRODUCT_GROUP} Website.url$\r$\n"
  WriteIniStr "$INSTDIR\Documentation.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}/doc/"
  FileWrite $UninstallLog "Documentation.url$\r$\n"
  WriteIniStr "$INSTDIR\New_Skins.url" "InternetShortcut" "URL" "${PRODUCT_WEB_SITE}/vlc/skins.php"
  FileWrite $UninstallLog "New_Skins.url$\r$\n"

  !insertmacro CloseUninstallLog

  ; Add VLC to "recommended programs" for the following extensions
  WriteRegStr HKCR Applications\vlc.exe "" ""
  WriteRegStr HKCR Applications\vlc.exe "FriendlyAppName" "VLC media player"
  WriteRegStr HKCR Applications\vlc.exe\shell\Open "" $ContextMenuEntry_PlayWith
  WriteRegStr HKCR Applications\vlc.exe\shell\Open\command "" '"$INSTDIR\vlc.exe" --started-from-file "%1"'
  !insertmacro MacroAllExtensions WriteRegStrSupportedTypes

; Windows default programs Registration
  ; Vista and above detection
  ReadRegStr $R0 HKLM "SOFTWARE\Microsoft\Windows NT\CurrentVersion" CurrentVersion
  StrCpy $R1 $R0 1
  StrCmp $R1 '6' lbl_vista lbl_done

  lbl_vista:
  WriteRegStr HKLM "Software\RegisteredApplications" "VLC" "Software\Clients\Media\VLC\Capabilities"
  WriteRegStr HKLM "Software\Clients\Media\VLC\Capabilities" "ApplicationName" "VLC media player"
  WriteRegStr HKLM "Software\Clients\Media\VLC\Capabilities" "ApplicationDescription" "VLC - The video swiss knife"
  WriteRegStr HKLM "Software\Clients\Media\VLC" "" "VLC media player"
  WriteRegStr HKLM "Software\Clients\Media\VLC\InstallInfo" "HideIconsCommand" "$\"$INSTDIR\spad-setup.exe$\" /HideIcons /S"
  WriteRegStr HKLM "Software\Clients\Media\VLC\InstallInfo" "ShowIconsCommand" "$\"$INSTDIR\spad-setup.exe$\" /ShowIcons /S"
  WriteRegStr HKLM "Software\Clients\Media\VLC\InstallInfo" "ReinstallCommand" "$\"$INSTDIR\spad-setup.exe$\" /Reinstall /S"
  WriteRegDWORD HKLM "Software\Clients\Media\VLC\InstallInfo" "IconsVisible" 0x001

  lbl_done:
${MementoSectionEnd}

${MementoSection} $Name_Section02a SEC02a
  SectionIn 1 2 3
  CreateDirectory "$SMPROGRAMS\VideoLAN"
  CreateShortCut "$SMPROGRAMS\VideoLAN\VLC media player.lnk" \
    "$INSTDIR\vlc.exe" ""
  CreateShortCut "$SMPROGRAMS\VideoLAN\VLC media player skinned.lnk" \
    "$INSTDIR\vlc.exe" "-Iskins"
  CreateShortCut "$SMPROGRAMS\VideoLAN\Documentation.lnk" \
    "$INSTDIR\Documentation.url"
  CreateShortCut "$SMPROGRAMS\VideoLAN\Release Notes.lnk" \
    "$INSTDIR\NEWS.txt" ""
  CreateShortCut "$SMPROGRAMS\VideoLAN\${PRODUCT_GROUP} Website.lnk" \
    "$INSTDIR\${PRODUCT_GROUP} Website.url"
  CreateShortCut "$SMPROGRAMS\VideoLAN\VLC media player - reset preferences and cache files.lnk" \
    "$INSTDIR\vlc.exe" "--reset-config --reset-plugins-cache vlc://quit"
${MementoSectionEnd}

${MementoSection} $Name_Section02b SEC02b
  SectionIn 1 2 3
  CreateShortCut "$DESKTOP\VLC media player.lnk" \
    "$INSTDIR\vlc.exe" ""
${MementoSectionEnd}

SectionGroup /e !$Name_Section34
!ifdef INSTALL_MOZILLA
${MementoSection} $Name_Section03 SEC03
  SectionIn 1 3

  SetOutPath "$INSTDIR"
  !insertmacro OpenUninstallLog
  !insertmacro InstallFile npvlc.dll
  !insertmacro InstallFile npvlc.dll.manifest
  !insertmacro CloseUninstallLog

  !define Moz "SOFTWARE\MozillaPlugins\@videolan.org/vlc,version=${VERSION}"
  WriteRegStr HKLM ${Moz} "Description" "VLC Multimedia Plugin"
  WriteRegStr HKLM ${Moz} "Path" "$INSTDIR\npvlc.dll"
  WriteRegStr HKLM ${Moz} "Product" "VLC media player"
  WriteRegStr HKLM ${Moz} "Vendor" "VideoLAN"
  WriteRegStr HKLM ${Moz} "Version" "${VERSION}"
${MementoSectionEnd}
!endif

!ifdef INSTALL_ACTIVEX
${MementoSection} $Name_Section04 SEC04
  SectionIn 1 3

  SetOutPath "$INSTDIR"
  !insertmacro OpenUninstallLog
  !insertmacro InstallFile axvlc.dll
  !insertmacro InstallFile axvlc.dll.manifest
  !insertmacro CloseUninstallLog
 RegDLL "$INSTDIR\axvlc.dll"
#  ExecWait 'regsvr32.exe /s "$INSTDIR\axvlc.dll"'
${MementoSectionEnd}
!endif
SectionGroupEnd


${MementoSection} $Name_Section05 SEC05
  SectionIn 1 2 3
  WriteRegStr HKCR "AudioCD\shell\PlayWithVLC" "" $ContextMenuEntry_PlayWith
  WriteRegStr HKCR "AudioCD\shell\PlayWithVLC\command" "" \
    '"$INSTDIR\vlc.exe" --started-from-file cdda:///%1'
  WriteRegStr HKCR "DVD\shell\PlayWithVLC" "" $ContextMenuEntry_PlayWith
  WriteRegStr HKCR "DVD\shell\PlayWithVLC\command" "" \
    '"$INSTDIR\vlc.exe" --started-from-file dvd:///%1'

  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayDVDMovieOnArrival" "VLCPlayDVDMovieOnArrival" ""
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDMovieOnArrival" "Action" $Action_OnArrivalDVD
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDMovieOnArrival" "DefaultIcon" '"$INSTDIR\vlc.exe",0'
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDMovieOnArrival" "InvokeProgID" "VLC.DVDMovie"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDMovieOnArrival" "InvokeVerb" "Open"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDMovieOnArrival" "Provider" "VideoLAN VLC media player"

  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayCDAudioOnArrival" "VLCPlayCDAudioOnArrival" ""
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayCDAudioOnArrival" "Action" $Action_OnArrivalAudioCD
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayCDAudioOnArrival" "DefaultIcon" '"$INSTDIR\vlc.exe",0'
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayCDAudioOnArrival" "InvokeProgID" "VLC.CDAudio"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayCDAudioOnArrival" "InvokeVerb" "Open"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayCDAudioOnArrival" "Provider" "VideoLAN VLC media player"

  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayVideoCDMovieOnArrival" "VLCPlayVCDMovieOnArrival" ""
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVCDMovieOnArrival" "Action" $Action_OnArrivalVCDMovie
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVCDMovieOnArrival" "DefaultIcon" '"$INSTDIR\vlc.exe",0'
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVCDMovieOnArrival" "InvokeProgID" "VLC.VCDMovie"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVCDMovieOnArrival" "InvokeVerb" "Open"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVCDMovieOnArrival" "Provider" "VideoLAN VLC media player"

  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlaySuperVideoCDMovieOnArrival" "VLCPlaySVCDMovieOnArrival" ""
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlaySVCDMovieOnArrival" "Action" $Action_OnArrivalSVCDMovie
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlaySVCDMovieOnArrival" "DefaultIcon" '"$INSTDIR\vlc.exe",0'
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlaySVCDMovieOnArrival" "InvokeProgID" "VLC.SVCDMovie"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlaySVCDMovieOnArrival" "InvokeVerb" "Open"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlaySVCDMovieOnArrival" "Provider" "VideoLAN VLC media player"

  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayDVDAudioOnArrival" "VLCPlayDVDAudioOnArrival" ""
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDAudioOnArrival" "Action" $Action_OnArrivalDVDAudio
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDAudioOnArrival" "DefaultIcon" '"$INSTDIR\vlc.exe",0'
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDAudioOnArrival" "InvokeProgID" "VLC.OPENFolder"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDAudioOnArrival" "InvokeVerb" "Open"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDAudioOnArrival" "Provider" "VideoLAN VLC media player"

  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayVideoFilesOnArrival" "VLCPlayVideoFilesOnArrival" ""
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVideoFilesOnArrival" "Action" $Action_OnArrivalVideoFiles
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVideoFilesOnArrival" "DefaultIcon" '"$INSTDIR\vlc.exe",0'
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVideoFilesOnArrival" "InvokeProgID" "VLC.OPENFolder"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVideoFilesOnArrival" "InvokeVerb" "Open"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVideoFilesOnArrival" "Provider" "VideoLAN VLC media player"

  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayMusicFilesOnArrival" "VLCPlayMusicFilesOnArrival" ""
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayMusicFilesOnArrival" "Action" $Action_OnArrivalMusicFiles
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayMusicFilesOnArrival" "DefaultIcon" '"$INSTDIR\vlc.exe",0'
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayMusicFilesOnArrival" "InvokeProgID" "VLC.OPENFolder"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayMusicFilesOnArrival" "InvokeVerb" "Open"
  WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayMusicFilesOnArrival" "Provider" "VideoLAN VLC media player"

  WriteRegStr HKCR "VLC.DVDMovie" "" "VLC DVD Movie"
  WriteRegStr HKCR "VLC.DVDMovie\shell" "" "Open"
  WriteRegStr HKCR "VLC.DVDMovie\shell\Open\command" "" \
    '"$INSTDIR\vlc.exe" --started-from-file dvd:///%1'
  WriteRegStr HKCR "VLC.DVDMovie\DefaultIcon" "" '"$INSTDIR\vlc.exe",0'

  WriteRegStr HKCR "VLC.CDAudio" "" "VLC CD Audio"
  WriteRegStr HKCR "VLC.CDAudio\shell" "" "Open"
  WriteRegStr HKCR "VLC.CDAudio\shell\Open\command" "" \
    '"$INSTDIR\vlc.exe" --started-from-file cdda:///%1'
  WriteRegStr HKCR "VLC.CDAudio\DefaultIcon" "" '"$INSTDIR\vlc.exe",0'

  WriteRegStr HKCR "VLC.VCDMovie" "" "VLC VCD Movie"
  WriteRegStr HKCR "VLC.VCDMovie\shell" "" "Open"
  WriteRegStr HKCR "VLC.VCDMovie\shell\Open\command" "" \
     '"$INSTDIR\vlc.exe" --started-from-file vcd:///%1'
  WriteRegStr HKCR "VLC.VCDMovie\DefaultIcon" "" '"$INSTDIR\vlc.exe",0'

  WriteRegStr HKCR "VLC.SVCDMovie" "" "VLC SVCD Movie"
  WriteRegStr HKCR "VLC.SVCDMovie\shell" "" "Open"
  WriteRegStr HKCR "VLC.SVCDMovie\shell\Open\command" "" \
     '"$INSTDIR\vlc.exe" --started-from-file vcd:///%1'
  WriteRegStr HKCR "VLC.SVCDMovie\DefaultIcon" "" '"$INSTDIR\vlc.exe",0'

  WriteRegStr HKCR "VLC.OPENFolder" "" "VLC Play content"
  WriteRegStr HKCR "VLC.OPENFolder\shell" "" "Open"
  WriteRegStr HKCR "VLC.OPENFolder\shell\Open\command" "" \
     '"$INSTDIR\vlc.exe" %1'
  WriteRegStr HKCR "VLC.OPENFolder\DefaultIcon" "" '"$INSTDIR\vlc.exe",0'

${MementoSectionEnd}


SectionGroup /e !$Name_Section06 SEC06
  SectionGroup $Name_SectionGroupAudio
    !insertmacro MacroAudioExtensions RegisterExtensionSection
  SectionGroupEnd
  SectionGroup $Name_SectionGroupVideo
    !insertmacro MacroVideoExtensions RegisterExtensionSection
  SectionGroupEnd
  SectionGroup $Name_SectionGroupOther
    !insertmacro MacroOtherExtensions RegisterExtensionSection
    !insertmacro MacroSkinExtensions RegisterSkinExtensionSection
  SectionGroupEnd
SectionGroupEnd

${MementoSection} $Name_Section07 SEC07
  SectionIn 1 3
  !insertmacro MacroAllExtensions AddContextMenu
  !insertmacro AddContextMenuExt "Directory"
${MementoSectionEnd}

${MementoUnselectedSection} $Name_Section08 SEC08
  !insertmacro delprefs
${MementoSectionEnd}

${MementoSectionDone}

; Installer section descriptions
!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC01} $Desc_Section01
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC02a} $Desc_Section02a
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC02b} $Desc_Section02b
!ifdef INSTALL_MOZILLA
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC03} $Desc_Section03
!endif
!ifdef INSTALL_ACTIVEX
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC04} $Desc_Section04
!endif
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC05} $Desc_Section05
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC06} $Desc_Section06
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC07} $Desc_Section07
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC08} $Desc_Section08
!insertmacro MUI_FUNCTION_DESCRIPTION_END

;;; Start function
Function .onInit

${MementoSectionRestore}

# !include "x64.nsh"
# ${Unless} ${RunningX64}
#    MessageBox MB_OK|MB_ICONSTOP "This version of VLC only runs on 64-bit operating systems.$\nPlease get the 32-bit version and try again."
#    Quit
# ${EndUnless}
${If} ${AtLeastWinXP}
    ${If} ${IsWinXP}
    ${AndIf} ${AtMostServicePack} 1
        Goto WinTooOld
    ${Endif}
${Else}
    Goto WinTooOld
${Endif}

# SetRegView 64
ReadRegStr $INSTDIR HKLM "${PRODUCT_DIR_REGKEY}" "InstallDir"
StrCmp $INSTDIR "" 0 UAC_Elevate
StrCpy $INSTDIR "$PROGRAMFILES\VideoLAN\VLC"

UAC_Elevate:
    UAC::RunElevated
    StrCmp 1223 $0 UAC_ElevationAborted
    StrCmp 0 $0 0 UAC_Err
    StrCmp 1 $1 0 UAC_Success
    Quit

UAC_Err:
    MessageBox mb_iconstop "Unable to elevate, error $0$\nPlease try to run this installer with admin privileges."
    Abort

UAC_ElevationAborted:
    MessageBox mb_iconstop "This installer requires admin privileges, aborting!"
    Abort

WinTooOld:
    MessageBox MB_OK|MB_ICONSTOP "This version of VLC only runs on Windows XP SP2 and newer."
    Quit

UAC_Success:
    StrCmp 1 $3 +4
    StrCmp 3 $1 0 UAC_ElevationAborted
    MessageBox mb_iconstop "This installer requires admin privileges, try again."
    goto UAC_Elevate

  ; /update argument
  Call ParseCommandline

  ; See if previous version exists
  Call ReadPreviousVersion

  ${If} $PreviousVersion == ""
    StrCpy $PerformUpdate 0
  ${Else}
    Push "${VERSION}"
    Push $PreviousVersion
    Call VersionCompare

    ${If} $PreviousVersionState != "newer"
      StrCpy $PerformUpdate 0
    ${EndIf}
  ${EndIf}

  !insertmacro MUI_LANGDLL_DISPLAY

  !include "languages\english.nsh"
  StrCmp $LANGUAGE ${LANG_FRENCH} French 0
  StrCmp $LANGUAGE ${LANG_GERMAN} German 0
  StrCmp $LANGUAGE ${LANG_ITALIAN} Italian 0
  StrCmp $LANGUAGE ${LANG_HUNGARIAN} Hungarian 0
  StrCmp $LANGUAGE ${LANG_ROMANIAN} Romanian 0
  StrCmp $LANGUAGE ${LANG_CATALAN} Catalan 0
  StrCmp $LANGUAGE ${LANG_BULGARIAN} Bulgarian 0
  StrCmp $LANGUAGE ${LANG_SLOVAK} Slovak 0
  StrCmp $LANGUAGE ${LANG_POLISH} Polish 0
  StrCmp $LANGUAGE ${LANG_DUTCH} Dutch 0
  StrCmp $LANGUAGE ${LANG_DANISH} Danish 0
  StrCmp $LANGUAGE ${LANG_SIMPCHINESE} SChinese 0
  StrCmp $LANGUAGE ${LANG_FINNISH} Finnish 0
  StrCmp $LANGUAGE ${LANG_JAPANESE} Japanese 0
;  StrCmp $LANGUAGE ${LANG_BENGALI} Bengali 0
;  StrCmp $LANGUAGE ${LANG_PUNJABI} Punjabi 0
;  StrCmp $LANGUAGE ${LANG_SLOVENIAN} Slovenian 0
  StrCmp $LANGUAGE ${LANG_SPANISH} Spanish 0
  StrCmp $LANGUAGE ${LANG_ESTONIAN} Estonian 0
  StrCmp $LANGUAGE ${LANG_LITHUANIAN} Lithuanian 0
  StrCmp $LANGUAGE ${LANG_BASQUE} Basque 0
  StrCmp $LANGUAGE ${LANG_SERBIAN} Serbian 0
  StrCmp $LANGUAGE ${LANG_RUSSIAN} Russian 0
  StrCmp $LANGUAGE ${LANG_HEBREW} Hebrew 0
  StrCmp $LANGUAGE ${LANG_GALICIAN} Galician 0
  StrCmp $LANGUAGE ${LANG_SWEDISH} Swedish 0
  StrCmp $LANGUAGE ${LANG_PORTUGUESEBR} Brazilian EndLanguageCmp
  French:
  !include "languages\french.nsh"
  Goto EndLanguageCmp
  German:
  !include "languages\german.nsh"
  Goto EndLanguageCmp
  Italian:
  !include "languages\italian.nsh"
  Goto EndLanguageCmp
  Hungarian:
  !include "languages\hungarian.nsh"
  Goto EndLanguageCmp
  Romanian:
  !include "languages\romanian.nsh"
  Goto EndLanguageCmp
  Catalan:
  !include "languages\catalan.nsh"
  Goto EndLanguageCmp
  Bulgarian:
  !include "languages\bulgarian.nsh"
  Goto EndLanguageCmp
  Slovak:
  !include "languages\slovak.nsh"
  Goto EndLanguageCmp
  Polish:
  !include "languages\polish.nsh"
  Goto EndLanguageCmp
  Dutch:
  !include "languages\dutch.nsh"
  Goto EndLanguageCmp
  Danish:
  !include "languages\danish.nsh"
  Goto EndLanguageCmp
  Schinese:
  !include "languages\schinese.nsh"
  Goto EndLanguageCmp
  Finnish:
  !include "languages\finnish.nsh"
  Goto EndLanguageCmp
  Japanese:
  !include "languages\japanese.nsh"
  Goto EndLanguageCmp
  Bengali:
  !include "languages\bengali.nsh"
  Goto EndLanguageCmp
  Punjabi:
  !include "languages\punjabi.nsh"
  Goto EndLanguageCmp
  Slovenian:
  !include "languages\slovenian.nsh"
  Goto EndLanguageCmp
  Spanish:
  !include "languages\spanish.nsh"
  Goto EndLanguageCmp
  Estonian:
  !include "languages\estonian.nsh"
  Goto EndLanguageCmp
  Lithuanian:
  !include "languages\lithuanian.nsh"
  Goto EndLanguageCmp
  Basque:
  !include "languages\basque.nsh"
  Goto EndLanguageCmp
  Serbian:
  !include "languages\serbian.nsh"
  Goto EndLanguageCmp
  Russian:
  !include "languages\Russian.nsh"
  Goto EndLanguageCmp
  Hebrew:
  !include "languages\hebrew.nsh"
  Goto EndLanguageCmp
  Galician:
  !include "languages\galician.nsh"
  Goto EndLanguageCmp
  Swedish:
  !include "languages\swedish.nsh"
  Goto EndLanguageCmp
  Brazilian:
  !include "languages\brazilian_portuguese.nsh"
  EndLanguageCmp:

  ;;ReadRegStr $R0  ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
  ;;"UninstallString"
  ;;StrCmp $R0 "" finished

  ;;MessageBox MB_YESNO|MB_ICONEXCLAMATION $Message_AlreadyInstalled /SD IDNO IDNO finished

  ;Run the uninstaller
  ;uninst:
    ClearErrors
    ExecWait '$R0 _?=$INSTDIR' ;Do not copy the uninstaller to a temp file
  finished:

FunctionEnd

;;; Page to upgrade / downgrade or customize the installation
Function PageReinstall
  ${If} $PreviousVersion == ""
    Abort
  ${EndIf}

  ${If} $PerformUpdate == 1
    StrCpy $ReinstallType 1
    Abort
  ${EndIf}

  nsDialogs::Create /NOUNLOAD 1018
  Pop $0

  ${If} $PreviousVersionState == "newer"

    !insertmacro MUI_HEADER_TEXT "Already Installed" "Choose how you want to install VLC."
    nsDialogs::CreateItem /NOUNLOAD STATIC ${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS} 0 0 0 100% 40 "An older version of VLC is installed on your system. Select the operation you want to perform and click Next to continue."
    Pop $R0
    nsDialogs::CreateItem /NOUNLOAD BUTTON ${BS_AUTORADIOBUTTON}|${BS_VCENTER}|${BS_MULTILINE}|${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS}|${WS_GROUP}|${WS_TABSTOP} 0 10 55 100% 30 "Upgrade VLC using previous settings (recommended)"
    Pop $ReinstallUninstallBtn
    nsDialogs::CreateItem /NOUNLOAD BUTTON ${BS_AUTORADIOBUTTON}|${BS_TOP}|${BS_MULTILINE}|${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS} 0 10 85 100% 50 "Change settings (advanced)"
    Pop $R0

    ${If} $ReinstallType == ""
      StrCpy $ReinstallType 1
    ${EndIf}

  ${ElseIf} $PreviousVersionState == "older"

    !insertmacro MUI_HEADER_TEXT "Already Installed" "Choose how you want to install VLC."
    nsDialogs::CreateItem /NOUNLOAD STATIC ${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS} 0 0 0 100% 40 "A newer version of VLC is already installed! It is not recommended that you downgrade to an older version. Select the operation you want to perform and click Next to continue."
    Pop $R0
    nsDialogs::CreateItem /NOUNLOAD BUTTON ${BS_AUTORADIOBUTTON}|${BS_VCENTER}|${BS_MULTILINE}|${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS}|${WS_GROUP}|${WS_TABSTOP} 0 10 55 100% 30 "Downgrade VLC using previous settings (recommended)"
    Pop $ReinstallUninstallBtn
    nsDialogs::CreateItem /NOUNLOAD BUTTON ${BS_AUTORADIOBUTTON}|${BS_TOP}|${BS_MULTILINE}|${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS} 0 10 85 100% 50 "Change settings (advanced)"
    Pop $R0

    ${If} $ReinstallType == ""
      StrCpy $ReinstallType 1
    ${EndIf}

  ${ElseIf} $PreviousVersionState == "same"

    !insertmacro MUI_HEADER_TEXT "Already Installed" "Choose the maintenance option to perform."
    nsDialogs::CreateItem /NOUNLOAD STATIC ${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS} 0 0 0 100% 40 "VLC ${VERSION} is already installed. Select the operation you want to perform and click Next to continue."
    Pop $R0
    nsDialogs::CreateItem /NOUNLOAD BUTTON ${BS_AUTORADIOBUTTON}|${BS_VCENTER}|${BS_MULTILINE}|${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS}|${WS_GROUP}|${WS_TABSTOP} 0 10 55 100% 30 "Add/Remove/Reinstall components"
    Pop $R0
    nsDialogs::CreateItem /NOUNLOAD BUTTON ${BS_AUTORADIOBUTTON}|${BS_TOP}|${BS_MULTILINE}|${WS_VISIBLE}|${WS_CHILD}|${WS_CLIPSIBLINGS} 0 10 85 100% 50 "Uninstall VLC"
    Pop $ReinstallUninstallBtn

    ${If} $ReinstallType == ""
      StrCpy $ReinstallType 2
    ${EndIf}

  ${Else}

    MessageBox MB_ICONSTOP "Unknown value of PreviousVersionState, aborting" /SD IDOK
    Abort

  ${EndIf}

  ${If} $ReinstallType == "1"
    SendMessage $ReinstallUninstallBtn ${BM_SETCHECK} 1 0
  ${Else}
    SendMessage $R0 ${BM_SETCHECK} 1 0
  ${EndIf}

  nsDialogs::Show

FunctionEnd

Function PageLeaveReinstall

  SendMessage $ReinstallUninstallBtn ${BM_GETCHECK} 0 0 $R0
  ${If} $R0 == 1
    ; Option to uninstall old version selected
    StrCpy $ReinstallType 1
  ${Else}
    ; Custom up/downgrade or add/remove/reinstall
    StrCpy $ReinstallType 2
  ${EndIf}

  ${If} $ReinstallType == 1

    ${If} $PreviousVersionState == "same"

      Call RunUninstaller
      Quit

    ${EndIf}

  ${EndIf}

FunctionEnd

Function RunUninstaller
  ReadRegStr $R1 ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" "UninstallString"
  ${If} $R1 == ""
    Return
  ${EndIf}

  ;Run uninstaller
  HideWindow
  ClearErrors

  ExecWait '$R1 _?=$INSTDIR'

  IfErrors no_remove_uninstaller

  IfFileExists "$INSTDIR\uninstall.exe" 0 no_remove_uninstaller
    Delete "$R1"
    RMDir $INSTDIR

  no_remove_uninstaller:
FunctionEnd

Function PageSkipPre
  ${If} $PerformUpdate == 1
    Abort
  ${ElseIf} $PreviousVersion != ""
    Abort
  ${EndIf}
FunctionEnd

;;; Used during upgrade to skip most pages
Function PageFastUpdatePre
  ${If} $PerformUpdate == 1
    Abort
  ${EndIf}
FunctionEnd

Function PageComponentsPre
  ClearErrors
  ReadRegStr $0 ${MEMENTO_REGISTRY_ROOT} `${MEMENTO_REGISTRY_KEY}` MementoSectionUsed
  ; Backward compatibility:
  ; Don't skip the components page until Memento was able to save the user choices.
  IfErrors done

  ${If} $ReinstallType == 1
    Abort
  ${EndIf}

done:
FunctionEnd

Function PageDirectoryPre
  ${If} $ReinstallType != ""
    Abort
  ${EndIf}
FunctionEnd

Function .OnInstFailed
    UAC::Unload
FunctionEnd

Function .OnInstSuccess
    ${MementoSectionSave}
    UAC::Unload
FunctionEnd

;; End function
Section -Post
  WriteUninstaller "$INSTDIR\uninstall.exe"
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "InstallDir" $INSTDIR
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "Version" "${VERSION}"
  WriteRegStr HKLM "${PRODUCT_DIR_REGKEY}" "" "$INSTDIR\vlc.exe"

  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "DisplayName" "$(^Name)"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "UninstallString" "$INSTDIR\uninstall.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "InstallLocation" "$INSTDIR"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "DisplayIcon" "$INSTDIR\vlc.exe"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "DisplayVersion" "${PRODUCT_VERSION}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "URLInfoAbout" "${PRODUCT_WEB_SITE}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "Publisher" "${PRODUCT_PUBLISHER}"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "VersionMajor"  "2"
  WriteRegStr ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}" \
    "VersionMinor" "1"
SectionEnd

;;;;;;;;;;;;;;;;;;;;;;;;
; Uninstaller sections ;
;;;;;;;;;;;;;;;;;;;;;;;;

Section "un.$Name_Section91" SEC91
  SectionIn 1 2 3 RO
  SetShellVarContext all

  !insertmacro MacroAllExtensions DeleteContextMenu
  !insertmacro MacroAllExtensions UnRegisterExtensionSection
  !insertmacro MacroSkinExtensions UnRegisterExtensionSection
  !insertmacro DeleteContextMenuExt "Directory"

  ;remove activex plugin
 UnRegDLL "$INSTDIR\axvlc.dll"
#  ExecWait 'regsvr32.exe /s /u "$INSTDIR\axvlc.dll"'
  Delete /REBOOTOK "$INSTDIR\axvlc.dll"
  Delete /REBOOTOK "$INSTDIR\axvlc.dll.manifest"

  ;remove mozilla plugin
  Push $R0
  Push $R1
  Push $R2

  !define Index 'Line${__LINE__}'
  StrCpy $R1 "0"

  "${Index}-Loop:"

    ; Check for Key
    EnumRegKey $R0 HKLM "SOFTWARE\Mozilla" "$R1"
    StrCmp $R0 "" "${Index}-End"
    IntOp $R1 $R1 + 1
    ReadRegStr $R2 HKLM "SOFTWARE\Mozilla\$R0\Extensions" "Plugins"
    StrCmp $R2 "" "${Index}-Loop" ""

    ; old files (0.8.5 and before) that may be lying around
    Delete /REBOOTOK "$R2\npvlc.dll"
    Delete /REBOOTOK "$R2\libvlc.dll"
    Delete /REBOOTOK "$R2\vlcintf.xpt"
    Goto "${Index}-Loop"

  "${Index}-End:"
  !undef Index
  Delete /REBOOTOK "$INSTDIR\npvlc.dll"
  Delete /REBOOTOK "$INSTDIR\npvlc.dll.manifest"

  RMDir "$SMPROGRAMS\VideoLAN"
  RMDir /r $SMPROGRAMS\VideoLAN

  FileOpen $UninstallLog "$INSTDIR\uninstall.log" r
  UninstallLoop:
    ClearErrors
    FileRead $UninstallLog $R0
    IfErrors UninstallEnd
    Push $R0
    Call un.TrimNewLines
    Pop $R0
    Delete "$INSTDIR\$R0"
    Goto UninstallLoop
  UninstallEnd:
  FileClose $UninstallLog
  Delete "$INSTDIR\uninstall.log"
  Delete "$INSTDIR\uninstall.exe"
  Push "\"
  Call un.RemoveEmptyDirs
  RMDir "$INSTDIR"

  DeleteRegKey HKLM Software\VideoLAN

  DeleteRegKey HKCR Applications\vlc.exe
  DeleteRegKey HKCR AudioCD\shell\PlayWithVLC
  DeleteRegKey HKCR DVD\shell\PlayWithVLC
  DeleteRegValue HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayDVDMovieOnArrival" "VLCPlayDVDMovieOnArrival"
  DeleteRegKey HKLM Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDMovieOnArrival
  DeleteRegValue HKLM "Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayCDAudioOnArrival" "VLCPlayCDAudioOnArrival"
  DeleteRegKey HKLM Software\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayCDAudioOnArrival
  DeleteRegValue HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayVideoCDMovieOnArrival" "VLCPlayVCDMovieOnArrival"
  DeleteRegKey HKLM SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVCDMovieOnArrival
  DeleteRegValue HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlaySuperVideoCDMovieOnArrival" "VLCPlaySVCDMovieOnArrival"
  DeleteRegKey HKLM SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlaySVCDMovieOnArrival
  DeleteRegValue HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayDVDAudioOnArrival" "VLCPlayDVDAudioOnArrival"
  DeleteRegKey HKLM SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayDVDAudioOnArrival
  DeleteRegValue HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayVideoFilesOnArrival" "VLCPlayVideoFilesOnArrival"
  DeleteRegKey HKLM SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayVideoFilesOnArrival
  DeleteRegValue HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\EventHandlers\PlayMusicFilesOnArrival" "VLCPlayMusicFilesOnArrival"
  DeleteRegKey HKLM SOFTWARE\Microsoft\Windows\CurrentVersion\Explorer\AutoplayHandlers\Handlers\VLCPlayMusicFilesOnArrival

  DeleteRegKey HKLM Software\Clients\Media\VLC
  DeleteRegValue HKLM "Software\RegisteredApplications" "VLC"
  DeleteRegKey HKCR "VLC.MediaFile"
  DeleteRegKey HKCR "VLC.DVDMovie"
  DeleteRegKey HKCR "VLC.CDAudio"
  DeleteRegKey HKCR "VLC.VCDMovie"
  DeleteRegKey HKCR "VLC.SVCDMovie"
  DeleteRegKey HKCR "VLC.OPENFolder"


  DeleteRegKey HKLM \
    "SOFTWARE\MozillaPlugins\@videolan.org/vlc,version=${VERSION}"

  DeleteRegKey HKLM \
    "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"

  Delete "$DESKTOP\VLC media player.lnk"

  DeleteRegKey ${PRODUCT_UNINST_ROOT_KEY} "${PRODUCT_UNINST_KEY}"
  DeleteRegKey HKLM "${PRODUCT_DIR_REGKEY}"
  SetAutoClose true
SectionEnd

Section /o "un.$Name_Section92" SEC92
  !insertmacro delprefs
SectionEnd

; Uninstaller section descriptions
!insertmacro MUI_UNFUNCTION_DESCRIPTION_BEGIN
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC91} $Desc_Section91
  !insertmacro MUI_DESCRIPTION_TEXT ${SEC92} $Desc_Section92
!insertmacro MUI_UNFUNCTION_DESCRIPTION_END

Function un.OnUnInstFailed
    UAC::Unload
FunctionEnd

Function un.OnUnInstSuccess
    UAC::Unload
    Delete "$INSTDIR\UAC.dll"
FunctionEnd

Function un.onInit

UAC_Elevate:
    UAC::RunElevated
    StrCmp 1223 $0 UAC_ElevationAborted
    StrCmp 0 $0 0 UAC_Err
    StrCmp 1 $1 0 UAC_Success
    Quit

UAC_Err:
    MessageBox mb_iconstop "Unable to elevate, error $0"
    Abort

UAC_ElevationAborted:
    MessageBox mb_iconstop "This installer requires admin access, aborting!"
    Abort

UAC_Success:
# SetRegView 64
    StrCmp 1 $3 +4
    StrCmp 3 $1 0 UAC_ElevationAborted
    MessageBox mb_iconstop "This installer requires admin access, try again"
    goto UAC_Elevate

  !insertmacro MUI_UNGETLANGUAGE

  !include "languages\english.nsh"
  StrCmp $LANGUAGE ${LANG_FRENCH} French 0
  StrCmp $LANGUAGE ${LANG_GERMAN} German 0
  StrCmp $LANGUAGE ${LANG_ITALIAN} Italian 0
  StrCmp $LANGUAGE ${LANG_HUNGARIAN} Hungarian 0
  StrCmp $LANGUAGE ${LANG_ROMANIAN} Romanian 0
  StrCmp $LANGUAGE ${LANG_CATALAN} Catalan 0
  StrCmp $LANGUAGE ${LANG_BULGARIAN} Bulgarian 0
  StrCmp $LANGUAGE ${LANG_SLOVAK} Slovak 0
  StrCmp $LANGUAGE ${LANG_POLISH} Polish 0
  StrCmp $LANGUAGE ${LANG_DUTCH} Dutch 0
  StrCmp $LANGUAGE ${LANG_DANISH} Danish 0
  StrCmp $LANGUAGE ${LANG_SIMPCHINESE} SChinese 0
  StrCmp $LANGUAGE ${LANG_FINNISH} Finnish 0
  StrCmp $LANGUAGE ${LANG_JAPANESE} Japanese 0
;  StrCmp $LANGUAGE ${LANG_BENGALI} Bengali 0
;  StrCmp $LANGUAGE ${LANG_PUNJABI} Punjabi 0
;  StrCmp $LANGUAGE ${LANG_SLOVENIAN} Slovenian 0
  StrCmp $LANGUAGE ${LANG_SPANISH} Spanish 0
  StrCmp $LANGUAGE ${LANG_ESTONIAN} Estonian 0
  StrCmp $LANGUAGE ${LANG_LITHUANIAN} Lithuanian 0
  StrCmp $LANGUAGE ${LANG_BASQUE} Basque 0
  StrCmp $LANGUAGE ${LANG_SERBIAN} Serbian 0
  StrCmp $LANGUAGE ${LANG_RUSSIAN} Russian 0
  StrCmp $LANGUAGE ${LANG_HEBREW} Hebrew 0
  StrCmp $LANGUAGE ${LANG_GALICIAN} Galician 0
  StrCmp $LANGUAGE ${LANG_SWEDISH} Swedish 0
  StrCmp $LANGUAGE ${LANG_PORTUGUESEBR} Brazilian EndLanguageCmp
  French:
  !include "languages\french.nsh"
  Goto EndLanguageCmp
  German:
  !include "languages\german.nsh"
  Goto EndLanguageCmp
  Italian:
  !include "languages\italian.nsh"
  Goto EndLanguageCmp
  Hungarian:
  !include "languages\hungarian.nsh"
  Goto EndLanguageCmp
  Romanian:
  !include "languages\romanian.nsh"
  Goto EndLanguageCmp
  Catalan:
  !include "languages\catalan.nsh"
  Goto EndLanguageCmp
  Bulgarian:
  !include "languages\bulgarian.nsh"
  Goto EndLanguageCmp
  Slovak:
  !include "languages\slovak.nsh"
  Goto EndLanguageCmp
  Polish:
  !include "languages\polish.nsh"
  Goto EndLanguageCmp
  Dutch:
  !include "languages\dutch.nsh"
  Goto EndLanguageCmp
  Danish:
  !include "languages\danish.nsh"
  Goto EndLanguageCmp
  Schinese:
  !include "languages\schinese.nsh"
  Goto EndLanguageCmp
  Finnish:
  !include "languages\finnish.nsh"
  Goto EndLanguageCmp
  Japanese:
  !include "languages\japanese.nsh"
  Goto EndLanguageCmp
  Bengali:
  !include "languages\bengali.nsh"
  Goto EndLanguageCmp
  Punjabi:
  !include "languages\punjabi.nsh"
  Goto EndLanguageCmp
  Slovenian:
  !include "languages\slovenian.nsh"
  Goto EndLanguageCmp
  Spanish:
  !include "languages\spanish.nsh"
  Goto EndLanguageCmp
  Estonian:
  !include "languages\estonian.nsh"
  Goto EndLanguageCmp
  Lithuanian:
  !include "languages\lithuanian.nsh"
  Goto EndLanguageCmp
  Basque:
  !include "languages\basque.nsh"
  Goto EndLanguageCmp
  Serbian:
  !include "languages\serbian.nsh"
  Goto EndLanguageCmp
  Russian:
  !include "languages\russian.nsh"
  Goto EndLanguageCmp
  Hebrew:
  !include "languages\hebrew.nsh"
  Goto EndLanguageCmp
  Galician:
  !include "languages\galician.nsh"
  Goto EndLanguageCmp
  Swedish:
  !include "languages\swedish.nsh"
  Goto EndLanguageCmp
  Brazilian:
  !include "languages\brazilian_portuguese.nsh"
  EndLanguageCmp:

FunctionEnd
