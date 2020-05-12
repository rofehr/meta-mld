DESCRIPTION = "Video Disk Recorder (VDR) is a digital sat-receiver program using Linux and DVB technologies. It allows to record broadcasts, as well as output the stream to TV."
SUMMARY = "Video Disk Recorder"
HOMEPAGE = "http://www.tvdr.de"
SECTION = "console/multimedia"
LICENSE = "GPLv2"
AUTHOR = "Klaus Schmidinger"

PR="r0"

# the current version
PV = "2.4.1"
SRC_URI = "ftp://ftp.tvdr.de/vdr/${P}.tar.bz2"
SRCREV = "ad10b92c314895c26a3bba1534b9d5ea6e3736ad"
SRC_URI[md5sum] = "b2897fe6b6e6711d512a69642b1b8ec1"
SRC_URI[sha256sum] = "25c3f835c4f3ff92cd2db10c004439ef22c2e895193c77fbe8cc7eac4858a1dc"
LIC_FILES_CHKSUM = "file://COPYING;md5=892f569a555ba9c07a568a7c0c4fa63a"

INSANE_SKIP += "pkgconfig"

SRC_URI_append = " \
        file://vdr-2.4.1-glibc-2.31.patch \   
        file://vdr.service \
        file://channels.conf \
        file://sources.conf \
        file://svdrphosts.conf \
        file://runvdr \	
    	file://etc/vdr/run.d/rc.action \ 
    	file://etc/vdr/run.d/vdr \      
        file://etc/host.conf \
        file://etc/hostname \
        file://etc/hosts \
        file://etc/hosts.allow \
        file://etc/hosts.deny \
"

inherit systemd pkgconfig gettext 

DEPENDS = " \
	fontconfig \
	freetype \
	ttf-bitstream-vera \
	gettext \
	jpeg \
	libcap \
	virtual/libintl \
	ncurses \
"

RDEPENDS_${PN} += "perl"

PLUGINDIR = "${libdir}/vdr"

CXXFLAGS = "-fPIC"



do_configure_append() {
    cat > Make.config <<-EOF
	## The C compiler options:
	CFLAGS   = ${CFLAGS} -Wall
	CXXFLAGS = ${CFLAGS} -Wall
	### The directory environment:
	PREFIX   = ${prefix}
	BINDIR   = ${bindir}
	INCDIR   = ${includedir}
	LIBDIR   = ${libdir}/vdr
	LOCDIR   = ${datadir}/locale
	MANDIR   = ${mandir}
	PCDIR    = ${libdir}/pkgconfig
	RESDIR   = ${datadir}/vdr

	VIDEODIR = /srv/vdr/video
	CONFDIR  = ${sysconfdir}/vdr
	ARGSDIR  = ${sysconfdir}/vdr/conf.d
	CACHEDIR = /var/cache/vdr
	EOF
}

do_compile () {
	oe_runmake 'DESTDIR=${D}' vdr
}


do_install () {
	oe_runmake 'DESTDIR=${D}' install-bin install-i18n install-includes install-pc

	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/vdr.service ${D}${systemd_unitdir}/system

    install -d ${D}/srv
    install -d ${D}/srv/vdr
    install -d ${D}/srv/vdr/video

    install -d ${D}/var/cache/vdr


    install -d ${D}${base_prefix}
    install -d ${D}${base_prefix}/sbin
    install -d ${D}${base_prefix}/etc
    install -d ${D}${base_prefix}/etc/vdr
    install -d ${D}${base_prefix}/etc/vdr/run.d
	
	install -m 0755 ${WORKDIR}/channels.conf  						${D}${base_prefix}/etc/vdr
    install -m 0755 ${WORKDIR}/sources.conf   						${D}${base_prefix}/etc/vdr
    install -m 0755 ${WORKDIR}/svdrphosts.conf                      ${D}${base_prefix}/etc/vdr  
    
    install -m 0755 ${WORKDIR}/runvdr         						${D}${base_prefix}/sbin
    
    install -m 0755 ${WORKDIR}/etc/vdr/run.d/vdr                   	${D}${base_prefix}/etc/vdr/run.d
    install -m 0755 ${WORKDIR}/etc/vdr/run.d/rc.action             	${D}${base_prefix}/etc/vdr/run.d
	
    install -m 0755    ${WORKDIR}/etc/hosts.allow               ${D}${base_prefix}/etc/hosts.allow
    install -m 0755    ${WORKDIR}/etc/hosts.deny                ${D}${base_prefix}/etc/hosts.deny
	
}

SYSTEMD_SERVICE_${PN} = "vdr.service" 
RPROVIDES_${PN} += "${PN}-systemd"
RREPLACES_${PN} += "${PN}-systemd"
RCONFLICTS_${PN} += "${PN}-systemd"

FILES_${PN} = " ${bindir}/* \
			    ${localstatedir}/cache/vdr \
			    ${datadir}/vdr \
			    ${systemd_unitdir}/system/vdr.service  \
				${base_prefix}/sbin/runvdr \
     			${base_prefix}/etc/vdr/channels.conf \
      			${base_prefix}/etc/vdr/sources.conf \
                ${base_prefix}/etc/vdr/svdrphosts.conf \
       			${base_prefix}/srv/vdr/video \
       			${base_prefix}/var/cache/vdr \
       			${base_prefix}/etc/vdr/run.d \
    			${base_prefix}/etc/vdr/run.d/vdr \
   				${base_prefix}/etc/vdr/run.d/rc.action \
                ${base_prefix}/etc/host.conf \
                ${base_prefix}/etc/hostname \
                ${base_prefix}/etc/hosts \
                ${base_prefix}/etc/hosts.allow \
                ${base_prefix}/etc/hosts.deny \
			    "


FILES_${PN}-dbg += "${PLUGINDIR}/.debug/*"

FILES_${PN}-locale = "${datadir}/locale"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


