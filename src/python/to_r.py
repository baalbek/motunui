#!/usr/bin/python3

import requests
import json

# url = "http://localhost:8082/maunaloa/to_r?oid=%s"
url = "https://hilo/maunaloa/to_r?oid=%s"

oid = "3"

r = requests.get(url % oid, verify=False)

j = json.loads(r.text)

ndx = j['ndx']
cc = j['cc']
cc_rf = j['cc_rf']
cc_ss = j['cc_ss']


for x in cc_rf: print (x)

"""
with open("../../R/%s.txt" % oid,'w') as f:
    f.write("n\tcc\tcc_ss\tcc_rf\n")
    for x in zip(ndx,cc,cc_ss,cc_rf):
        f.write("%d\t%.15f\t%.15f\t%.15f\n" % (x[0],x[1],x[2],x[3]))
"""


