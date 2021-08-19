SUMMARY = "rpi-eeprom "
LICENSE = "CLOSED"
COMPATIBLE_MACHINE = "(raspberrypi|raspberrypi2|raspberrypi3|raspberrypi4|raspberrypi4-64)"

PR = "r1"

SRC_URI = "git://github.com/raspberrypi/rpi-eeprom.git"
SRCREV = "8dac90ae9a3bc7a9543e056c983de73e88ea1d79"

do_install () {
  install -d ${D}${sysconfdir}/    
  install -d ${D}${sysconfdir}/default/    

	install -m 755 ${WORKDIR}/git/rpi-eeprom-update ${D}${sysconfdir}/default/rpi-eeprom-update
  
  install -d ${D}${base_libdir}/    
  install -d ${D}${base_libdir}/firmware/    
  
	install -m 755 ${WORKDIR}/git/firmware/beta/*.bin ${D}${base_libdir}/firmware/
  
}

FILES_${PN} = " ${sysconfdir}/default/rpi-eeprom-update \
                ${base_libdir}/firmware/*.bin \
                "
