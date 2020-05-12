SUMMARY = "(MLD) kernel-modules group"
PR = "r1"

inherit packagegroup

RDEPENDS_${PN} = "\
    kernel-modules \
"
