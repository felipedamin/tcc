import pandas as pd
import os

def parse_line(line):
    line = line.strip()
    split_string = line.split("#")
    class_name = split_string[0]
    method_name = split_string[1]
    statement_type = split_string[2]
    condition = split_string[3]
    condition_value = split_string[4]
    return {
        'class#method#statement_type#condition': [class_name + '#' + method_name + '#' + statement_type + '#' + condition],
        'condition_value': [condition_value]
    }

def heuristic_zero_executions(df):
    df_clean = df.drop_duplicates(subset='class#method#statement_type#condition', keep=False)
    return df_clean

def generate_logfile_dict():
    parsed_lines = dict()
    # TODO: mudar "teste" para "xisnove"
    with open(os.path.abspath('src/main/java/teste/logFile/logFile.out')) as fp:
        line_number = 1
        while True:
            line = fp.readline()
            if not line:
                break
            parsed_lines[line_number] = parse_line(line)
            line_number += 1
    return parsed_lines

def generate_suspect_executions():
    parsed_lines = generate_logfile_dict()
    dfs_to_concat = []
    columns = ['class#method#statement_type#condition', 'condition_value']
    for line in parsed_lines.values():
        df = pd.DataFrame(line)
        dfs_to_concat.append(df)
    df = pd.concat(dfs_to_concat, ignore_index=True)
    df["count"] = 1
    df = df.groupby(columns)["count"].count().reset_index()
    df_clean = heuristic_zero_executions(df)
    # TODO: mudar "teste" para "xisnove"
    frequency_analyser_dir = 'src/main/java/teste/frequency_analyser'
    file_name = os.path.join(frequency_analyser_dir, 'conditions_of_interest.out')
    df_clean = df_clean['class#method#statement_type#condition']
    df_clean.to_csv(file_name, sep=' ', encoding='utf-8', index=False, header=False)

if __name__ == "__main__":
    generate_suspect_executions()
