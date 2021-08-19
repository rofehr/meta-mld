SUMMARY = "A complete, cross-platform solution to record, convert and stream audio and video."
DESCRIPTION = "FFmpeg is the leading multimedia framework, able to decode, encode, transcode, \
               mux, demux, stream, filter and play pretty much anything that humans and machines \
               have created. It supports the most obscure ancient formats up to the cutting edge."
HOMEPAGE = "https://www.ffmpeg.org/"
SECTION = "libs"

LICENSE = "CLOSED"
LICENSE_FLAGS = "commercial"


#SRC_URI = " \
#  git://github.com/jc-kynesim/rpi-ffmpeg.git;branch=dev/4.3.1/drm_prime_1 \
#  "
#SRCREV = "9ccd76db7f0d866bd5e8902c4a2eb9ef0f0dcf7d"

SRC_URI = " \
  git://github.com/jc-kynesim/rpi-ffmpeg.git;branch=test/4.3.2/rpi_main \
  "
SRCREV = "9f4662e6270a22d826babd3d4d12683984a1d8e3"


#SRC_URI[sha256sum] = "d2782de974fc43b850f0104b27f26712755069265c282b79a82bcf157ac3b6e2"

S = "${WORKDIR}/git"


# Build fails when thumb is enabled: https://bugzilla.yoctoproject.org/show_bug.cgi?id=7717
ARM_INSTRUCTION_SET_armv4 = "arm"
ARM_INSTRUCTION_SET_armv5 = "arm"
ARM_INSTRUCTION_SET_armv6 = "arm"

# Should be API compatible with libav (which was a fork of ffmpeg)
# libpostproc was previously packaged from a separate recipe
PROVIDES = "libav libpostproc"

DEPENDS = "nasm-native libdrm udev "

RDEPENDS_${PN} +=" libdrm udev "

inherit autotools pkgconfig

PACKAGECONFIG ??= "avdevice avfilter avcodec avformat swresample swscale postproc avresample \
                   alsa bzlib lzma pic pthreads shared theora zlib libdrm \
                   ${@bb.utils.contains('AVAILTUNES', 'mips32r2', 'mips32r2', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xv xcb', '', d)}"

# libraries to build in addition to avutil
PACKAGECONFIG[avdevice] = "--enable-avdevice,--disable-avdevice"
PACKAGECONFIG[avfilter] = "--enable-avfilter,--disable-avfilter"
PACKAGECONFIG[avcodec] = "--enable-avcodec,--disable-avcodec"
PACKAGECONFIG[avformat] = "--enable-avformat,--disable-avformat"
PACKAGECONFIG[swresample] = "--enable-swresample,--disable-swresample"
PACKAGECONFIG[swscale] = "--enable-swscale,--disable-swscale"
PACKAGECONFIG[postproc] = "--enable-postproc,--disable-postproc"
PACKAGECONFIG[avresample] = "--enable-avresample,--disable-avresample"
PACKAGECONFIG[libdrm] = "--enable-libdrm,--disable-libdrm"

# features to support
PACKAGECONFIG[alsa] = "--enable-alsa,--disable-alsa,alsa-lib"
PACKAGECONFIG[altivec] = "--enable-altivec,--disable-altivec,"
PACKAGECONFIG[bzlib] = "--enable-bzlib,--disable-bzlib,bzip2"
PACKAGECONFIG[fdk-aac] = "--enable-libfdk-aac --enable-nonfree,--disable-libfdk-aac,fdk-aac"
PACKAGECONFIG[gpl] = "--enable-gpl,--disable-gpl"
PACKAGECONFIG[gsm] = "--enable-libgsm,--disable-libgsm,libgsm"
PACKAGECONFIG[jack] = "--enable-indev=jack,--disable-indev=jack,jack"
PACKAGECONFIG[libvorbis] = "--enable-libvorbis,--disable-libvorbis,libvorbis"
PACKAGECONFIG[lzma] = "--enable-lzma,--disable-lzma,xz"
PACKAGECONFIG[mfx] = "--enable-libmfx,--disable-libmfx,intel-mediasdk"
PACKAGECONFIG[mp3lame] = "--enable-libmp3lame,--disable-libmp3lame,lame"
PACKAGECONFIG[openssl] = "--enable-openssl,--disable-openssl,openssl"
PACKAGECONFIG[sdl2] = "--enable-sdl2,--disable-sdl2,virtual/libsdl2"
PACKAGECONFIG[speex] = "--enable-libspeex,--disable-libspeex,speex"
PACKAGECONFIG[srt] = "--enable-libsrt,--disable-libsrt,srt"
PACKAGECONFIG[theora] = "--enable-libtheora,--disable-libtheora,libtheora libogg"
PACKAGECONFIG[vaapi] = "--enable-vaapi,--disable-vaapi,libva"
PACKAGECONFIG[vdpau] = "--enable-vdpau,--disable-vdpau,libvdpau"
PACKAGECONFIG[vpx] = "--enable-libvpx,--disable-libvpx,libvpx"
PACKAGECONFIG[x264] = "--enable-libx264,--disable-libx264,x264"
PACKAGECONFIG[x265] = "--enable-libx265,--disable-libx265,x265"
PACKAGECONFIG[xcb] = "--enable-libxcb,--disable-libxcb,libxcb"
PACKAGECONFIG[xv] = "--enable-outdev=xv,--disable-outdev=xv,libxv"
PACKAGECONFIG[zlib] = "--enable-zlib,--disable-zlib,zlib"

# other configuration options
PACKAGECONFIG[mips32r2] = ",--disable-mipsdsp --disable-mipsdspr2"
PACKAGECONFIG[pic] = "--enable-pic"
PACKAGECONFIG[pthreads] = "--enable-pthreads,--disable-pthreads"
PACKAGECONFIG[shared] = "--enable-shared"
PACKAGECONFIG[strip] = ",--disable-stripping"

# Check codecs that require --enable-nonfree
USE_NONFREE = "${@bb.utils.contains_any('PACKAGECONFIG', [ 'openssl' ], 'yes', '', d)}"

def cpu(d):
    for arg in (d.getVar('TUNE_CCARGS') or '').split():
        if arg.startswith('-mcpu='):
            return arg[6:]
    return 'generic'

EXTRA_OECONF = " \
    ${@bb.utils.contains('USE_NONFREE', 'yes', '--enable-nonfree', '', d)} \
    \
    --cross-prefix=${TARGET_PREFIX} \
    \
    --ld="${CCLD}" \
    --cc="${CC}" \
    --cxx="${CXX}" \
    --arch=${TARGET_ARCH} \
    --target-os="linux" \
    --enable-cross-compile \
    --extra-cflags="${CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}" \
    --extra-ldflags="${LDFLAGS}" \
    --sysroot="${STAGING_DIR_TARGET}" \
    ${EXTRA_FFCONF} \
    --libdir=${libdir} \
    --shlibdir=${libdir} \
    --datadir=${datadir}/ffmpeg \
    --cpu=${@cpu(d)} \
    --pkg-config=pkg-config \
    --disable-debug \
    --enable-sand \
    --enable-libudev \
    --enable-v4l2_m2m \
    --enable-v4l2-request \
    --enable-libdrm \
    --enable-libx265 \
    --disable-rpi \
    --enable-gpl \
    --enable-libx264 \
    --enable-pic \
    --enable-shared \
    --enable-vout-drm \
    --enable-avfilter \
    --enable-nonfree \
    --enable-gpl \
    --enable-iconv \
    --enable-network \
    --enable-pthreads \
    --disable-vdpau \
    --disable-vaapi \
    --enable-libfdk-aac \
    --enable-version3 \
"

EXTRA_OECONF_append_linux-gnux32 = " --disable-asm"
# gold crashes on x86, another solution is to --disable-asm but thats more hacky
# ld.gold: internal error in relocate_section, at ../../gold/i386.cc:3684

LDFLAGS_append_x86 = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

EXTRA_OEMAKE = "V=1"

do_configure() {
    ${S}/configure ${EXTRA_OECONF}
}

# patch out build host paths for reproducibility
do_compile_prepend_class-target() {
        sed -i -e "s,${WORKDIR},,g" ${B}/config.h
}

do_install_append() {
     #rm ${D}/usr/share/ffmpeg 
     rm ${D}/usr/share/ffmpeg/libvpx-720p.ffpreset
     rm ${D}/usr/share/ffmpeg/libvpx-360p.ffpreset
     rm ${D}/usr/share/ffmpeg/libvpx-720p50_60.ffpreset
     rm ${D}/usr/share/ffmpeg/libvpx-1080p50_60.ffpreset
     rm ${D}/usr/share/ffmpeg/ffprobe.xsd
     rm ${D}/usr/share/ffmpeg/libvpx-1080p.ffpreset
     rm ${D}/usr/share/ffmpeg/examples/decode_audio.c
     rm ${D}/usr/share/ffmpeg/examples/scaling_video.c
     rm ${D}/usr/share/ffmpeg/examples/transcode_aac.c
     rm ${D}/usr/share/ffmpeg/examples/demuxing_decoding.c
     rm ${D}/usr/share/ffmpeg/examples/vaapi_transcode.c
     rm ${D}/usr/share/ffmpeg/examples/qsvdec.c
     rm ${D}/usr/share/ffmpeg/examples/resampling_audio.c
     rm ${D}/usr/share/ffmpeg/examples/remuxing.c
     rm ${D}/usr/share/ffmpeg/examples/filtering_video.c
     rm ${D}/usr/share/ffmpeg/examples/avio_list_dir.c
     rm ${D}/usr/share/ffmpeg/examples/encode_video.c
     rm ${D}/usr/share/ffmpeg/examples/Makefile
     rm ${D}/usr/share/ffmpeg/examples/filtering_audio.c
     rm ${D}/usr/share/ffmpeg/examples/vaapi_encode.c
     rm ${D}/usr/share/ffmpeg/examples/decode_video.c
     rm ${D}/usr/share/ffmpeg/examples/README
     rm ${D}/usr/share/ffmpeg/examples/hw_decode.c
     rm ${D}/usr/share/ffmpeg/examples/transcoding.c
     rm ${D}/usr/share/ffmpeg/examples/avio_reading.c
     rm ${D}/usr/share/ffmpeg/examples/extract_mvs.c
     rm ${D}/usr/share/ffmpeg/examples/muxing.c
     rm ${D}/usr/share/ffmpeg/examples/http_multiclient.c
     rm ${D}/usr/share/ffmpeg/examples/metadata.c
     rm ${D}/usr/share/ffmpeg/examples/encode_audio.c
     rm ${D}/usr/share/ffmpeg/examples/filter_audio.c
     
     rm -r ${D}/usr/share/ffmpeg

}

#PACKAGES =+ "libavcodec \
#"


#PACKAGES =+ "libavcodec \
#             libavdevice \
#             libavfilter \
#             libavformat \
#             libavresample \
#             libavutil \
#             libpostproc \
#             libswresample \
#             libswscale"

#FILES_libavcodec = "${libdir}/libavcodec${SOLIBS}"
#FILES_libavdevice = "${libdir}/libavdevice${SOLIBS}"
#FILES_libavfilter = "${libdir}/libavfilter${SOLIBS}"
#FILES_libavformat = "${libdir}/libavformat${SOLIBS}"
#FILES_libavresample = "${libdir}/libavresample${SOLIBS}"
#FILES_libavutil = "${libdir}/libavutil${SOLIBS}"
#FILES_libpostproc = "${libdir}/libpostproc${SOLIBS}"
#FILES_libswresample = "${libdir}/libswresample${SOLIBS}"
#FILES_libswscale = "${libdir}/libswscale${SOLIBS}"

# ffmpeg disables PIC on some platforms (e.g. x86-32)
INSANE_SKIP_${MLPREFIX}libavcodec = "textrel"
INSANE_SKIP_${MLPREFIX}libavdevice = "textrel"
INSANE_SKIP_${MLPREFIX}libavfilter = "textrel"
INSANE_SKIP_${MLPREFIX}libavformat = "textrel"
INSANE_SKIP_${MLPREFIX}libavutil = "textrel"
INSANE_SKIP_${MLPREFIX}libavresample = "textrel"
INSANE_SKIP_${MLPREFIX}libswscale = "textrel"
INSANE_SKIP_${MLPREFIX}libswresample = "textrel"
INSANE_SKIP_${MLPREFIX}libpostproc = "textrel"
