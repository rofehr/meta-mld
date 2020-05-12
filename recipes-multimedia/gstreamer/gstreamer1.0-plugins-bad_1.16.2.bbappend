PACKAGECONFIG_append_rpi = " hls libmms kms \
                   ${@bb.utils.contains('LICENSE_FLAGS_WHITELIST', 'commercial', 'faad', '', d)}"
