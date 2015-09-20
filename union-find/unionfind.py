import sys

class DSNode:
    def __init__(self, parent, value):
        self.parent = parent
        self.value = value
        self.height = 1

nodes = [None]*1000000

def create(val):
    newnode = DSNode(None, val)
    newnode.parent = newnode
    nodes[val] = newnode

def findnode(xnode):
    if xnode.parent != xnode:
        xnode.parent = findnode(xnode.parent)
    return xnode.parent

def find(x):
    return findnode(nodes[x])

def same(x, y):
    if x == y:
        return True
    if not nodes[x] or not nodes[y]:
        return False
    return find(nodes[x].value).value == find(nodes[y].value).value

def union(x, y):
    if not nodes[x]:
        create(x)
    if not nodes[y]:
        create(y)

    x_rep = find(x)
    y_rep = find(y)

    if x_rep == y_rep:
        return

    if x_rep.height > y_rep.height:
        y_rep.parent = x_rep
    elif y_rep.height > x_rep.height:
        x_rep.parent = y_rep
    elif x_rep.height == y_rep.height:
        x_rep.parent = y_rep
        y_rep.height += 1

N, Q = [int(x) for x in sys.stdin.readline().split(" ")]

for _ in range(Q):
    op, set1, set2 = sys.stdin.readline().strip("\n").split(" ")
    set1 = int(set1)
    set2 = int(set2)
    
    if op == "?":
        print "yes" if same(set1, set2) else "no"
        
    if op == "=":
        union(set1, set2)