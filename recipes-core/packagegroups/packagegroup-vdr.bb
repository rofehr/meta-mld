SUMMARY = "(MLD) vdr group"
PR = "r1"

inherit packagegroup

RDEPENDS_${PN} = "\
    curl \ 
    vdr-font-symbols \
    libio-socket-ssl-perl \
    startvdr \
    vdr \
    vdr-locale-de-de \
    vdr-plugin-satip \
    vdr-plugin-gstreamerdevice \ 
" 