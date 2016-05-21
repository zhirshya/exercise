#!/bin/sh
exec tail -n +3 $0
# This file provides an easy way to add custom menu entries.  Simply type the
# menu entries you want to add after this comment.  Be careful not to change
# the 'exec tail' line above.
menuentry "Ubuntu 16.04 Desktop ISO centos_root" {
loopback loop /ubuntu-16.04-desktop-amd64.iso
linux (loop)/casper/vmlinuz boot=casper iso-scan/filename=/ubuntu-16.04-desktop-amd64.iso noeject noprompt splash --
initrd (loop)/casper/initrd.lz
}
menuentry "Ubuntu 16.04 Desktop ISO /dev/sdb" {
loopback loop /dev/sdb/ubuntu-16.04-desktop-amd64.iso
linux (loop)/casper/vmlinuz boot=casper iso-scan/filename=/dev/sdb/ubuntu-16.04-desktop-amd64.iso noeject noprompt splash --
initrd (loop)/casper/initrd.lz
}
menuentry "Ubuntu 16.04 Desktop ISO/dev/sdb1" {
loopback loop /dev/sdb1/ubuntu-16.04-desktop-amd64.iso
linux (loop)/casper/vmlinuz boot=casper iso-scan/filename=/dev/sdb1/ubuntu-16.04-desktop-amd64.iso noeject noprompt splash --
initrd (loop)/casper/initrd.lz
}
menuentry "Ubuntu 16.04 Desktop ISO/dev/sdb2" {
loopback loop /dev/sdb2/ubuntu-16.04-desktop-amd64.iso
linux (loop)/casper/vmlinuz boot=casper iso-scan/filename=/dev/sdb2/ubuntu-16.04-desktop-amd64.iso noeject noprompt splash --
initrd (loop)/casper/initrd.lz
}
menuentry "Ubuntu 16.04 Desktop ISO/dev/sdb3/" {
loopback loop /dev/sdb3/ubuntu-16.04-desktop-amd64.iso
linux (loop)/casper/vmlinuz boot=casper iso-scan/filename=/dev/sdb3/ubuntu-16.04-desktop-amd64.iso noeject noprompt splash --
initrd (loop)/casper/initrd.lz
}
