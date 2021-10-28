import pprint
import pandas as pd

def parse_line(line):
    split_first_hashtag = line.split('#', 1)
    before_hashtag = split_first_hashtag[0]
    after_hashtag = split_first_hashtag[1]
    split_colon = after_hashtag.split(':', 1)
    containing_class = before_hashtag.split(' ', 1)[1]
    containing_method = split_colon[0]
    line_number = split_colon[1].split(' ', 1)[0]
    branch_or_invoked_method = split_colon[1].split(' ', 1)[1].rstrip('\n')
    if branch_or_invoked_method.split('--> ')[0] != branch_or_invoked_method:
        branch = ''
        invoked_method = branch_or_invoked_method.split('--> ')[1]
        invoked_method_owner = invoked_method.split('#')[0]
        invoked_method = invoked_method.split('#')[1]
    else:
        branch = branch_or_invoked_method
        invoked_method = ''
        invoked_method_owner = ''
    return {
        'containing_class': [containing_class],
        'containing_method': [containing_method],
        'line_number': [line_number],
        'branch': [branch],
        'invoked_method_owner': [invoked_method_owner],
        'invoked_method': [invoked_method]
    }

parsed_lines = dict()
with open("/Users/victorkim/Downloads/cobertura.out") as fp:
    line_number = 1
    while True:
        line = fp.readline()
        if not line:
            break
        parsed_lines[line_number] = parse_line(line)
        line_number += 1

# pp = pprint.PrettyPrinter(indent=4)
# pp.pprint(parsed_lines)

columns = ['containing_class', 'containing_method', 'line_number', 'branch', 'invoked_method_owner', 'invoked_method']

dfs_to_concat = []

for line in parsed_lines.values():
    df = pd.DataFrame(line)
    dfs_to_concat.append(df)

df = pd.concat(dfs_to_concat, ignore_index=True)

df["count"] = 1
df = df.groupby(columns)["count"].count().reset_index()


file_name = "/Users/victorkim/Documents/df.csv"

df.to_csv(file_name, sep='\t', encoding='utf-8', index=False)

print(df)

print(df['containing_class'].unique())