SUMMARY = "A small image just capable of allowing a device to boot."

LABELS = "mld"

inherit core-image


#SPLASH = "psplash-raspberrypi"

IMAGE_INSTALL = " \
    packagegroup-core-boot \ 
    "

IMAGE_INSTALL += " \
    kernel-modules \
    psplash \
    e2fsprogs-resize2fs \
    ca-certificates \
    "

IMAGE_FEATURES += " splash package-management ssh-server-openssh"

IMAGE_LINGUAS = " "
LABELS = "mld"

LICENSE = "MIT"

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"
