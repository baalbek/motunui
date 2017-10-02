#!/usr/bin/python3

import requests
import json

# url = "http://localhost:8082/maunaloa/to_r?oid=%s"
# url = "https://hilo/maunaloa/to_r?oid=%s"
url = "https://andromeda/maunaloa/to_r?oid=%s"

oid = "3"

r = requests.get(url % oid, verify=False)

j = json.loads(r.text)

def write_to_R(is_daily):
    if is_daily == True:
        ndx = j['ndx']
        cc = j['cc']
        cc_rf = j['cc_rf']
        cc_ss = j['cc_ss']
        fname = "../../R/%s.txt" % oid
    else:
        ndx = j['w_ndx']
        cc = j['w_cc']
        cc_rf = j['w_cc_rf']
        cc_ss = j['w_cc_ss']
        fname = "../../R/%s_w.txt" % oid
    with open(fname,'w') as f:
        f.write("n\tcc\tcc_ss\tcc_rf\n")
        for x in zip(ndx,cc,cc_ss,cc_rf):
            f.write("%d\t%.15f\t%.15f\t%.15f\n" % (x[0],x[1],x[2],x[3]))

write_to_R(True)
write_to_R(False)


