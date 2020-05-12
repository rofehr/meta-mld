SUMMARY = "VDR HD output device "
LICENSE = "CLOSED"
#COMPATIBLE_MACHINE = "(raspberrypi|raspberrypi2|raspberrypi3|raspberrypi4|raspberrypi4-64)"

PR = "r1"

SRC_URI = "git://github.com/FernetMenta/vdr-plugin-vnsiserver.git"
SRCREV = "ce9f23ddfbe33973e7a66fb5f990d70dc806a46e"

S = "${WORKDIR}/git"

inherit pkgconfig gettext

ASNEEDED = ""

DEPENDS = " \
	vdr \
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

