import sys
import itertools

"""
procedure karatsuba(num1, num2)
  if (num1 < 10) or (num2 < 10)
    return num1*num2
  /* calculates the size of the numbers */
  m = max(size_base10(num1), size_base10(num2))
  m2 = m/2
  /* split the digit sequences about the middle */
  high1, low1 = split_at(num1, m2)
  high2, low2 = split_at(num2, m2)
  /* 3 calls made to numbers approximately half the size */
  z0 = karatsuba(low1,low2)
  z1 = karatsuba((low1+high1),(low2+high2))
  z2 = karatsuba(high1,high2)
  return (z2*10^(2*m2))+((z1-z2-z0)*10^(m2))+(z0)
"""

def quadmul(pol1, pol2):
    result = [0]*(len(pol1)+len(pol2))
    for index1, coeff1 in enumerate(pol1):
        for index2, coeff2 in enumerate(pol2):
            result[index1+index2] += coeff1*coeff2
    return result

def polyplus(pol1, pol2):
    val = [0]*max(len(pol1), len(pol2))
    for i in range(max(len(pol1), len(pol2))):
        if i >= len(pol1) and i >= len(pol2):
            val[i] = 0
        elif i >= len(pol1):
            val[i] = pol2[i]
        elif i >= len(pol2):
            val[i] = pol1[i]
        else:
            val[i] = pol1[i] + pol2[i]
    return val

def polyminus(pol1, pol2):
    val = [0]*max(len(pol1), len(pol2))
    for i in range(max(len(pol1), len(pol2))):
        if i >= len(pol1) and i >= len(pol2):
            val[i] = 0
        elif i >= len(pol1):
            val[i] = pol2[i]
        elif i >= len(pol2):
            val[i] = pol1[i]
        else:
            val[i] = pol1[i] - pol2[i]
    return val

def karatsuba(pol1, pol2):
    if len(pol1) < 4 or len(pol2) < 4:
        return quadmul(pol1, pol2)
    m = max(len(pol1), len(pol2))
    m2 = m // 2
    low1 = pol1[:m2]
    high1 = pol1[m2:]
    low2 = pol2[:m2]
    high2 = pol2[m2:]

    z0 = karatsuba(low1, low2)
    z1 = karatsuba((polyplus(low1,high1)), (polyplus(low2,high2)))
    z2 = karatsuba(high1, high2)

    ar1 = [0]*(2*m2+1)
    ar1[2*m2] = z2

    ar2 = polyminus(polyminus(z1, z2), z0)

    return polyplus(quadmul(z2, ar1), polyplus(quadmul(polyminus(polyminus(z1, z2), z0), ar2), z0))

T = int(sys.stdin.readline().strip("\n"))
for _ in range(T):
    deg1 = int(sys.stdin.readline().strip("\n"))
    pol1 = [int(x) for x in sys.stdin.readline().strip("\n").split(" ")]
    deg2 = int(sys.stdin.readline().strip("\n"))
    pol2 = [int(x) for x in sys.stdin.readline().strip("\n").split(" ")]

    res = karatsuba(pol1, pol2)
    
    deg = 0
    for index, coeff in enumerate(res):
        if coeff != 0:
            deg = index

    print deg
    print " ".join([str(x) for x in res[:deg+1]])