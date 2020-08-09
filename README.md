# Cublic Spline Interpolation

Compute the coefficients for a series of equations that pass through a given set of points.
Each equation corresponds to the equation of a line going between two.

# Example Usage

    Given the input "3;1,2,3,4;0,2,6,4" corresponding to the points 
    (1,0), (2,2), (3,6), and (4,4).
    
    The program will return:
    j          a[j]       b[j]       c[j]       d[j]
    0          0          1.0667     0          0.9334
    1          2          3.8667     2.8        -2.6666
    2          6          1.4667     -5.2       1.7334
    
    This corresponds to the equations:
    y = 0 + 1.0667*(x-1) + 0.0*(x-1)^2 + 0.9334*(x-1)^3 for [1,2]
    y = 2 + 3.8667*(x-2) + 2.8*(x-2)^2 - 2.6666*(x-2)^3 for [2,3]
    y = 6 + 1.4667*(x-3) - 5.2*(x-3)^2 + 1.7334*(x-3)^3 for [3,4]
