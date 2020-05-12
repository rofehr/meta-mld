SUMMARY = "(MLD) alsa group"
PR = "r1"

inherit packagegroup

RDEPENDS_${PN} = "\
    alsa-lib \
    alsa-tools \
    alsa-utils \
    alsa-ucm-conf \
"
