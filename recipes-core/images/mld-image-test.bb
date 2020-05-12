SUMMARY = "A small image just capable of allowing a device to boot."

LABELS = "mld"

#SPLASH = "psplash-raspberrypi"

IMAGE_INSTALL = " \
    packagegroup-core-boot \ 
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

IMAGE_INSTALL += " \
    psplash \
    packagegroup-kernel-modules \
    packagegroup-tools \
    packagegroup-vdr \
    packagegroup-gstreamer \
    packagegroup-alsa \
    "

IMAGE_FEATURES += " splash package-management ssh-server-openssh"

#PACKAGE_INSTALL += " \
#    kernel-modules \
#    psplash \
#    mc \
#    startvdr \
#    vdr-font-symbols \
#    vdr \
#    vdr-locale-de-de \
#    gstreamer1.0-libav \ 
#    gstreamer1.0-plugins-bad \
#    gstreamer1.0-plugins-base \
#    gstreamer1.0-plugins-good \
#    gstreamer1.0-plugins-ugly \
#    vdr-plugin-gstreamerdevice \
#    alsa-lib \
#    alsa-tools \
#    alsa-utils \
#    alsa-ucm-conf \
#    vdr-plugin-satip \
#    htop \
#    nano \
#    localedef \
#    ffmpeg \
#"

IMAGE_LINGUAS = " "
LABELS = "mld"

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
