def foo():
    z = set()
    for i in range(1,3):
        for j in range(1,3):
            z.add(i + j)
    z.add(42)
    print z ## value z => set([2,3,4,5,6,42])
