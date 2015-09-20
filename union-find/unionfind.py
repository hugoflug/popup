import sys

class DSNode:
    def __init__(self, parent, value):
        self.parent = parent
        self.value = value
        self.height = 1

N, Q = [int(x) for x in sys.stdin.readline().split(" ")]

nodes = {}

def create(val):
    nodes[val] = DSNode(None, val)

def find(x):
    xnode = nodes[x]

    while xnode.parent:
        xnode = xnode.parent
    return xnode

def same(x, y):
    if x not in nodes or y not in nodes:
        return False
    return find(nodes[x].value).value == find(nodes[y].value).value

def union(x, y):
    if x not in nodes:
        create(x)
    if y not in nodes:
        create(y)

    x_rep = find(x)
    y_rep = find(y)

    if x_rep.height > y_rep.height:
        y_rep.parent = x_rep
    elif y_rep.height > x_rep.height:
        x_rep.parent = y_rep
    elif x_rep.height == y_rep.height:
        x_rep.parent = y_rep
        y_rep.height += 1



for _ in range(Q):
    op, set1, set2 = sys.stdin.readline().strip("\n").split(" ")
    
    if op == "?":
        print("yes" if same(set1, set2) else "no")
        
    if op == "=":
        union(set1, set2)