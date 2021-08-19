PACKAGECONFIG_append_rpi = " hls libmms kms \
                   ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'commercial', 'faad', '', d)}"
PACKAGECONFIG_append_rock-pi-4b= " hls libmms kms\
                   ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'commercial', 'faad', '', d)}"