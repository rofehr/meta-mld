SUMMARY = "VDR skindesigner plugin"
LICENSE = "GPLv2" 
LIC_FILES_CHKSUM = "file://COPYING;md5=892f569a555ba9c07a568a7c0c4fa63a"

SRCREV = "a685cbb131d5e62f30582fc8ab61a02b420512a8"
SRC_URI = "git://projects.vdr-developer.org/git/vdr-plugin-skindesigner.git;protocol=http \
           file://vdr-skindesigner-makefile.patch \
           "
PR="r23"

S = "${WORKDIR}/git"

inherit pkgconfig gettext

DEPENDS = " \
	cairo \
	librsvg \
	libxml2 \
	jpeg \
	libpng  \
	curl \
	font-opensans \
	vdr \
"

RDEPENDS_${PN} += "bash"

EXTRA_OEMAKE = ' SDKSTAGE="${STAGING_DIR_HOST}" '
TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
	oe_runmake DESTDIR=${D} PREFIX=/usr install
	
	install -d ${D}/usr/share/fonts
	install -m 0755 ${S}/fonts/VDROpenSans/* ${D}/usr/share/fonts
}

FILES_${PN} = " \
	${libdir}/libskindesignerapi* \
	${libdir}/vdr/* \
	${sysconfdir}/vdr/themes/* \
	${datadir}/vdr/plugins/skindesigner/* \
	${datadir}/fonts \
"

FILES_${PN}-dbg += " \
	${libdir}/vdr/.debug/* \
"
FILES_${PN}-locale = "${datadir}/locale"
