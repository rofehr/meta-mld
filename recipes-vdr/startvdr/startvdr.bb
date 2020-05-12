SUMMARY = " start vdr script"
LICENSE = "CLOSED"

SRC_URI = " file://startvdr \
          "

PR="r3"

S = "${WORKDIR}/git"

inherit systemd

DEPENDS = " \
	procps \
	util-linux \
"

RDEPENDS_${PN} += "bash"

do_install() {
	install -d ${D}${bindir}/    
	install -m 0755 ${WORKDIR}/startvdr ${D}${bindir}/startvdr
}																																													
 
FILES_${PN} = " ${bindir}/startvdr \
                "

