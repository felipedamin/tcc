import pprint
import pandas as pd
import os

test_line = "class#methodName#(if|for|while|switch)#[a<10,b!=a]#[a,b]#[a.toString(),b.toString()]#[true,false]"

test_line_2 = "class#methodName#a<10 && b!=a#true"

def parse_line(line):
    line = line.strip()
    split_string = line.split("#")
    class_name = split_string[0]
    method_name = split_string[1]
    condition = split_string[2]
    condition_value = split_string[3]
    return {
        'class#method#condition': [class_name + '#' + method_name + '#' + condition],
        'condition_value': [condition_value]
    }

def heuristic_zero_executions(df):
    df_clean = df.drop_duplicates(subset='class#method#condition', keep=False)
    return df_clean

parsed_lines = dict()
with open('/Users/victorkim/Documents/POLI/TCC/tcc/logFile.out') as fp:
    line_number = 1
    while True:
        line = fp.readline()
        if not line:
            break
        parsed_lines[line_number] = parse_line(line)
        line_number += 1

columns = ['class', 'method', 'statement_type', 'condition', 'tokens', 'token_values', 'condition_value']

columns_2 = ['class#method#condition', 'condition_value']

dfs_to_concat = []

for line in parsed_lines.values():
    df = pd.DataFrame(line)
    dfs_to_concat.append(df)

df = pd.concat(dfs_to_concat, ignore_index=True)

df["count"] = 1
df = df.groupby(columns_2)["count"].count().reset_index()

df_clean = heuristic_zero_executions(df)

file_name = "/Users/victorkim/Documents/df_clean.out"

df_clean = df_clean['class#method#condition']
df_clean.to_csv(file_name, sep=' ', encoding='utf-8', index=False, header=False)

# print(df)

# print(df['containing_class'].unique())