SUMMARY = "mlist plugin for VDR"
AUTHOR = "Joachim Wilke"
LICENSE = "CLOSED"

PR = "r3"

SRC_URI = "git://github.com/vdr-projects/vdr-plugin-mpv.git"

SRCREV = "6b5fd095ad9a787c501b1151c9db45cee05b0dc8"

SRC_URI[md5sum] = "b356bf311309d97960bce879e574c4cb"
SRC_URI[sha256sum] = "62d069d87f75e32d10eec0d4e37a1a2294d361d244e37e062dc212500bfb49a3"

S = "${WORKDIR}/git"

inherit pkgconfig gettext

DEPENDS = "vdr mpv libdrm"
RDEPENDS_${PN} = "vdr mpv libdrm"

CXXFLAGS_append = " -fPIC -D_FILE_OFFSET_BITS=64 -D_LARGEFILE_SOURCE -D_LARGEFILE64_SOURCE  -DUSE_LIBMPV -DUSE_DRM"

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

