SUMMARY = "VDR HD output device "
LICENSE = "CLOSED"
#COMPATIBLE_MACHINE = "(raspberrypi|raspberrypi2|raspberrypi3|raspberrypi4|raspberrypi4-64)"

PR = "r1"

SRC_URI = "git://github.com/zillevdr/vdr-plugin-softhddevice-drm.git;branch=drm"
SRCREV = "8d84e6ef7707a40e43ef33492e4b500df9109570"

#SRC_URI += " \
#    file://rpihddevice-opt-vc.diff \
#"

S = "${WORKDIR}/git"

inherit pkgconfig gettext

ASNEEDED = ""

DEPENDS = " \
	vdr \
    ffmpeg \
    freetype \
    virtual/libgl \
    virtual/libgles2 \
    virtual/egl \
    libmms \
"


CXXFLAGS_append = " -fPIC -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE -D_LARGEFILE64_SOURCE"

EXTRA_OEMAKE = ' \
    STRIP=/bin/true \
'

do_install() {
    oe_runmake DESTDIR=${D} install
}

FILES_${PN} += " \
    ${libdir}/vdr/* \
"

FILES_${PN}-dbg += " \
    ${libdir}/vdr/.debug/* \
"
FILES_${PN}-locale = "${datadir}/locale"

