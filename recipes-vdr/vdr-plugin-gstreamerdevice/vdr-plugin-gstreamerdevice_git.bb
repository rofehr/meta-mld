SUMMARY = "VDR HD output device "
LICENSE = "CLOSED"
#COMPATIBLE_MACHINE = "(raspberrypi|raspberrypi2|raspberrypi3|raspberrypi4|raspberrypi4-64)"

PR = "r1"

SRC_URI = "git://github.com/rofehr/gstreamerdevice.git;branch=kms"
SRCREV = "5668fae5ff72f7726210fcc25a21fcac28b82d8a"

S = "${WORKDIR}/git"

inherit pkgconfig gettext

ASNEEDED = ""

DEPENDS = " \
	vdr \
	gstreamer1.0 \
	gstreamer1.0-libav \
	cairo \
	libxcomposite \
	libxrender \
	libxpm \
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

