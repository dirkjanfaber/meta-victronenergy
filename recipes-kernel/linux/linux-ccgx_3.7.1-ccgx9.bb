# fix behavior of base do_install_prepend - its overwrite ready to use uImage by uncompresses Image
require linux-ccgx.inc

# Mind it, this recipe is not installed itself but provides kernel-image etc.
# Hence RPEDEND on that one....
RDEPENDS_kernel-image = "linux-backports"
BB_FETCH_PREMIRRORONLY = "0"
PREMIRRORS = ""

SRC_URI = "https://github.com/victronenergy/linux/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "a5470a58ffdee1fb029b5f443185a215"
SRC_URI[sha256sum] = "becf6c16240fa2ec17dbd1b60666e1f7e361037ce7cd66e1a382b0d21b880c10"

# This was introduced to remove uImage from /boot and save 3MB
KERNEL_DROPIMAGE = ""

PR = "r10"

S = "${WORKDIR}/linux-${PV}"

pkg_postinst_kernel-base_append() {
	if [ "x$D" == "x" ]; then
		if [ -e /proc/mtd ]; then
			MTD_DEV=`grep kernel /proc/mtd`
			LINUX_DEV=${MTD_DEV:0:4}
		else
			echo "ERROR: No MTD device"
			exit 1;
		fi
	
		if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ] && [ -n $LINUX_DEV ]; then
			echo "INFO: Erasing $LINUX_DEV"
			flash_erase  /dev/$LINUX_DEV 0 0
			echo "INFO: Write Linux kernel > $LINUX_DEV"
			nandwrite -p /dev/$LINUX_DEV /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
			rm /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
			echo "Update finished!"
		else
			echo "ERROR: No kernel (/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}) image found!"
			exit 1
		fi
	else
		# Exit 1 is used to set the status of the package on unpacked in rootfs image
		# The result is that the package will be installed on first boot
		exit 1
	fi
}
