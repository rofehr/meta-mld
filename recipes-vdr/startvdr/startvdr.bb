SUMMARY = " start vdr script"
LICENSE = "CLOSED"

SRC_URI = " \
  file://startvdr \
  file://startvdr.service \ 
          "

PR="r4"

S = "${WORKDIR}/git"

inherit systemd
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "startvdr.service" 

DEPENDS = " \
	procps \
	util-linux \
"

RDEPENDS_${PN} += "bash"

do_install() {
	install -d ${D}${bindir}/    
	install -m 0755 ${WORKDIR}/startvdr ${D}${bindir}/startvdr
  install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/startvdr.service ${D}${systemd_unitdir}/system 
}																																													
 
FILES_${PN} = " \
  ${bindir}/startvdr \
  ${systemd_unitdir}/system/startvdr.service  \ 
                "

