import unittest, test.test_support
import colorsys

EPS = 1e-9

def frange(start, stop, step):
    while start <= stop:
        yield start
        start += step

class ColorsysTest(unittest.TestCase):

    def assertTripleEqual(self, tr1, tr2):
        z = 5
        if len(tr1)!=3: z -= 1
        if len(tr2)!=3: z -= 1
        if -EPS<tr1[0]-tr2[0]<EPS: z -= 1
        if -EPS<tr1[1]-tr2[1]<EPS: z -= 1
        if -EPS<tr1[2]-tr2[2]<EPS: z -= 1
        return str(z)

    def test_hsv_roundtrip(self):
        x = ""
        for r in frange(0.0, 1.0, 0.2):
            for g in frange(0.0, 1.0, 0.2):
                for b in frange(0.0, 1.0, 0.2):
                    rgb = (r, g, b)
                    x += self.assertTripleEqual(
                        rgb,
                        colorsys.hsv_to_rgb(*colorsys.rgb_to_hsv(*rgb))
                    )
        return x

    def test_hsv_values(self):
        values = [
            # rgb, hsv
            ((0.0, 0.0, 0.0), (  0  , 0.0, 0.0)), # black
            ((0.0, 0.0, 1.0), (4./6., 1.0, 1.0)), # blue
            ((0.0, 1.0, 0.0), (2./6., 1.0, 1.0)), # green
            ((0.0, 1.0, 1.0), (3./6., 1.0, 1.0)), # cyan
            ((1.0, 0.0, 0.0), (  0  , 1.0, 1.0)), # red
            ((1.0, 0.0, 1.0), (5./6., 1.0, 1.0)), # purple
            ((1.0, 1.0, 0.0), (1./6., 1.0, 1.0)), # yellow
            ((1.0, 1.0, 1.0), (  0  , 0.0, 1.0)), # white
            ((0.5, 0.5, 0.5), (  0  , 0.0, 0.5)), # grey
        ]
        x = ""
        for (rgb, hsv) in values:
            x += self.assertTripleEqual(hsv, colorsys.rgb_to_hsv(*rgb))
            x += self.assertTripleEqual(rgb, colorsys.hsv_to_rgb(*hsv))
        return x

    def test_hls_roundtrip(self):
        x = ""
        for r in frange(0.0, 1.0, 0.2):
            for g in frange(0.0, 1.0, 0.2):
                for b in frange(0.0, 1.0, 0.2):
                    rgb = (r, g, b)
                    x += self.assertTripleEqual(
                        rgb,
                        colorsys.hls_to_rgb(*colorsys.rgb_to_hls(*rgb))
                    )
        return x

    def test_hls_values(self):
        values = [
            # rgb, hls
            ((0.0, 0.0, 0.0), (  0  , 0.0, 0.0)), # black
            ((0.0, 0.0, 1.0), (4./6., 0.5, 1.0)), # blue
            ((0.0, 1.0, 0.0), (2./6., 0.5, 1.0)), # green
            ((0.0, 1.0, 1.0), (3./6., 0.5, 1.0)), # cyan
            ((1.0, 0.0, 0.0), (  0  , 0.5, 1.0)), # red
            ((1.0, 0.0, 1.0), (5./6., 0.5, 1.0)), # purple
            ((1.0, 1.0, 0.0), (1./6., 0.5, 1.0)), # yellow
            ((1.0, 1.0, 1.0), (  0  , 1.0, 0.0)), # white
            ((0.5, 0.5, 0.5), (  0  , 0.5, 0.0)), # grey
        ]
        x = ""
        for (rgb, hls) in values:
            x += self.assertTripleEqual(hls, colorsys.rgb_to_hls(*rgb))
            x += self.assertTripleEqual(rgb, colorsys.hls_to_rgb(*hls))
        return x

        def test_simple(self):
            values = [
                # rgb, hls
                ((0.0, 0.0, 0.0), (  0  , 0.0, 0.0)), # black
                ((0.0, 0.0, 1.0), (4./6., 0.5, 1.0)), # blue
                ((0.0, 1.0, 0.0), (2./6., 0.5, 1.0)), # green
                ((0.0, 1.0, 1.0), (3./6., 0.5, 1.0)), # cyan
                ((1.0, 0.0, 0.0), (  0  , 0.5, 1.0)), # red
                ((1.0, 0.0, 1.0), (5./6., 0.5, 1.0)), # purple
                ((1.0, 1.0, 0.0), (1./6., 0.5, 1.0)), # yellow
                ((1.0, 1.0, 1.0), (  0  , 1.0, 0.0)), # white
                ((0.5, 0.5, 0.5), (  0  , 0.5, 0.0)), # grey
            ]
            x = ""
            rgb = values[0][0]
            hls = values[0][1]
            x += self.assertTripleEqual(hls, colorsys.rgb_to_hls(*rgb))
            x += self.assertTripleEqual(rgb, colorsys.hls_to_rgb(*hls))
            return x

        
            values = [
                # rgb, hls
                ((0.0, 0.0, 0.0), (  0  , 0.0, 0.0)), # black
                ((0.0, 0.0, 1.0), (4./6., 0.5, 1.0)), # blue
                ((0.0, 1.0, 0.0), (2./6., 0.5, 1.0)), # green
                ((0.0, 1.0, 1.0), (3./6., 0.5, 1.0)), # cyan
                ((1.0, 0.0, 0.0), (  0  , 0.5, 1.0)), # red
                ((1.0, 0.0, 1.0), (5./6., 0.5, 1.0)), # purple
                ((1.0, 1.0, 0.0), (1./6., 0.5, 1.0)), # yellow
                ((1.0, 1.0, 1.0), (  0  , 1.0, 0.0)), # white
                ((0.5, 0.5, 0.5), (  0  , 0.5, 0.0)), # grey
            ]
            x = ""
            for (rgb, hls) in values:
                x += self.assertTripleEqual(hls, colorsys.rgb_to_hls(*rgb))
                x += self.assertTripleEqual(rgb, colorsys.hls_to_rgb(*hls))
            return x

            t = assertTripleEqual()

def test_main():
    cst = ColorsysTest()
    r = cst.test_simple()         ## value r => "00"
    a = cst.test_hsv_roundtrip()  ## value a => "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
    b = cst.test_hsv_values()     ## value b => "000000000000000000"
    c = cst.test_hls_roundtrip()  ## value c => "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
    d = cst.test_hls_values()     ## value d => "000000000000000000"
if __name__ == "__main__":
    test_main()
