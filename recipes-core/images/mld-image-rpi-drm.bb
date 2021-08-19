SUMMARY = "A small image just capable of allowing a device to boot."

LABELS = "mld"

#SPLASH = "psplash-raspberrypi"

IMAGE_INSTALL = " \
    packagegroup-core-boot \ 
    ${CORE_IMAGE_EXTRA_INSTALL} \
    "

IMAGE_INSTALL += " \
    packagegroup-kernel-modules \
    packagegroup-tools \
    packagegroup-alsa \
    "

IMAGE_INSTALL += "\
    curl \ 
    vdr-font-symbols \
    libio-socket-ssl-perl \
    startvdr \
    vdr \
    vdr-locale-de-de \
    vdr-plugin-satip \
    vdr-plugin-softhddevice-drm \ 
" 

IMAGE_INSTALL += " \
    gdb \
    webserver \
"

IMAGE_FEATURES += " package-management ssh-server-openssh"

IMAGE_LINGUAS = " "
LABELS = "mld"

LICENSE = "MIT"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
