
def bozz
	x = 1 
	ty x	## not-cached localvar-type x
	ty x	## expr x => Fixnum
	ty x	## cached localvar-type x
end
