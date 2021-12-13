import numpy as np
from numba import cuda

s1 = "ATGTTAT"
s2 = "ATCGTAC"
s2[1]

w = len(s1) + 1
v = len(s2) + 1

dp = np.zeros([w, v])


# @cuda.jit(device=True)
def printLCS(dp, s1, s2):
    for i in range(1, len(s1) + 1):
        for j in range(1, len(s2) + 1):
            tmp = 0
            if s1[i - 1] == s2[j - 1]:
                tmp = 1
            dp[i, j] = max(dp[i - 1, j - 1] + tmp, dp[i - 1, j], dp[i, j - 1])
    return dp


import itertools
import numpy as np


def matrix(a, b, match_score=3, gap_cost=2):
    H = np.zeros((len(a) + 1, len(b) + 1))
    for i, j in itertools.product(range(1, H.shape[0]), range(1, H.shape[1])):
        match = H[i - 1, j - 1] + (match_score if a[i - 1] == b[j - 1] else - match_score)
        delete = H[i - 1, j] - gap_cost
        insert = H[i, j - 1] - gap_cost
        H[i, j] = max(match, delete, insert, 0)
    return H


def traceback(H, b, b_='', old_i=0):
    # flip H to get index of **last** occurrence of H.max() with np.argmax()
    H_flip = np.flip(np.flip(H, 0), 1)
    i_, j_ = np.unravel_index(H_flip.argmax(), H_flip.shape)
    i, j = np.subtract(H.shape, (i_ + 1, j_ + 1))  # (i, j) are **last** indexes of H.max()
    if H[i, j] == 0:
        return b_, j
    b_ = b[j - 1] + '-' + b_ if old_i - i > 1 else b[j - 1] + b_
    return traceback(H[0:i, 0:j], b, b_, i)


def smith_waterman(a, b, match_score=3, gap_cost=2):
    a, b = a.upper(), b.upper()
    H = matrix(a, b, match_score, gap_cost)
    b_, pos = traceback(H, b)
    return pos, pos + len(b_)
