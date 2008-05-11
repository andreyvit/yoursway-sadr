"""
Convenience wrapper around rpm.hdr class.

rpm.hdr provides all the RPM fields by the indexes defined in rpm module, which
is not convenient. This wrapper adds RpmHeader class with .field_name access to
the RPM header values.

Accessors are not generated on-the-fly, so dir(RpmHeader) and code completion in
interactive shell will work properly.
"""

class RpmHeader(object):
    def __init__(self, hdr):
        self.hdr = hdr

    def __repr__(self):
        if self.epoch:
            return "<RPM header %d:%s-%s.%s>" % (self.epoch, self.name, self.version, self.release)
        else:
            return "<RPM header %s-%s.%s>" % (self.name, self.version, self.release)

def _add_getter_to_rpmheader(name, value):
    def getter(self):
        return self.hdr[value]
    setattr(RpmHeader, name, property(getter))

def _populate_rpm_header_class():
    import rpmstub

    for tag in dir(rpmstub):
        if tag.startswith('RPMTAG_'):
            _add_getter_to_rpmheader(tag[7:].lower(), getattr(rpm, tag))

_populate_rpm_header_class()

def read_file(f):
    import rpmstub
    return map(RpmHeader, rpmstub.readHeaderListFromFile(f))

s = read_file(None)
z = repr(s) ## value z => "<RPM header 7:3proxy-0.5.3h.alt1>"

