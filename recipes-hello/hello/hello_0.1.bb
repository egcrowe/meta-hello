DESCRIPTION = "Simple helloworld erlang application"
SECTION = "examples"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

SRC_URI  = "git://github.com/egcrowe/hello.git;tag=0.1;protocol=http"

S = "${WORKDIR}/git"

SRC_DIR = "${libdir}/erlang/lib/${P}/src"
EBIN_DIR = "${libdir}/erlang/lib/${P}/ebin"
CONF_DIR = "${sysconfdir}/hello.d"

# The following is a workaround to prevent AC_ERLANG_NEED_ERL autoconf macro
# failing due running erl program compiled for arm running on x86_64
EXTRA_OECONF = "--host=x86_64-linux --target=x86_64-linux"

inherit autotools update-rc.d

INITSCRIPT_NAME = "hello.otp.system"
INITSCRIPT_PARAMS = "defaults 75"

do_install_append() {
    install -d ${D}${SRC_DIR}
    install -m 0755 ${S}/src/hello.app.src ${D}${SRC_DIR}
    install -m 0755 ${S}/src/hello.erl ${D}${SRC_DIR}
    install -d ${D}${EBIN_DIR}
    install -m 0755 ${S}/src/hello.app ${D}${EBIN_DIR}
    install -m 0755 ${S}/src/hello.beam ${D}${EBIN_DIR}
    install -d ${D}${CONF_DIR}
    install -m 0755 ${S}/sys/hello.config ${D}${CONF_DIR}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/sys/hello.otp.system ${D}${sysconfdir}/init.d
    install -d ${D}${bindir}
    install -m 0755 ${S}/sys/hello.start ${D}${bindir}
    install -m 0755 ${S}/sys/hello.stop ${D}${bindir}
}

FILES_${PN}  = "${SRC_DIR}/hello.app.src"
FILES_${PN} += "${SRC_DIR}/hello.erl"
FILES_${PN}  = "${EBIN_DIR}/hello.app"
FILES_${PN} += "${EBIN_DIR}/hello.beam"
FILES_${PN} += "${CONF_DIR}/hello.config"
FILES_${PN} += "${sysconfdir}/init.d/hello.otp.system"
FILES_${PN} += "${bindir}/hello.start"
FILES_${PN} += "${bindir}/hello.stop"
