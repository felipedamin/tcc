import re
import json
from collections import Counter

method_list = []

with open('fuzz-log.out') as f:
  for l in f:
    result = re.search("(?:\(\d{1,9}\) )([\w/#<>$]{1,})", l)
    if result != None:
        method_list.append(str(result.group(1)))

with open('coverage.out') as f:
  for l in f:
    result = re.search("([\w/#<>$]{1,})", l)
    if result != None:
        method_list.append(str(result.group(1)))

frequency_dict = dict(Counter(method_list))

methods_to_instrument = []
for key, value in frequency_dict.items():
  if(value < 3):
    methods_to_instrument.append(key)

#with open('frequency.json', 'w') as fp:
#    json.dump(frequency_dict, fp)

with open('instrument.out', 'w') as f:
    for item in methods_to_instrument:
        f.write("%s\n" % item)

print('Done!')