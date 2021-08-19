SUMMARY = "(MLD) gstreamer group"
PR = "r1"

inherit packagegroup

RDEPENDS_${PN} = "\
    gstreamer1.0 \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-ugly \
"

RDEPENDS_${PN}_rock-pi-4b += " \
    gstreamer1.0-rockchip \
    rockchip-mpp \
    "
