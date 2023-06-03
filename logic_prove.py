import os
import json


def read_json_to_dict(path: str = '') -> list:
    obj = None
    try:
        with open(path, 'r') as file_in:
            obj = json.load(fp=file_in)
    except Exception as err:
        print(err)

    return obj


if __name__ == "__main__":
    base_values_path = os.path.join('app', 'src' , 'test' , 'resources', 'testcase1', 'baseValues.json')
    rules_path = os.path.join('app', 'src' , 'test' , 'resources', 'testcase1', 'rules.json')

    base_values = read_json_to_dict(base_values_path)
    rules = read_json_to_dict(rules_path)
    for rule in rules:
        rule['percent'] = rule['percent']/100
    anticipate_vaules = [None] * len(base_values)

    cumulative = 0
    vi = 0  # value index
    ri = 0  # rule index
    while vi < len(base_values):
        anticipate_vaule = 0
        cumulative += base_values[vi]

        if rules[ri]['end'] != -1:
            if cumulative < rules[ri]['end']:
                anticipate_vaule = base_values[vi] * rules[ri]['percent']

            elif cumulative > rules[ri]['end']:
                under = rules[ri]['end'] - (cumulative - base_values[vi])
                anticipate_vaule = under * rules[ri]['percent']
                over = cumulative - rules[ri]['end']
                ri += 1
                anticipate_vaule += over * rules[ri]['percent']
    
            else:  # equal
                anticipate_vaule = base_values[vi] * rules[ri]['percent']
                ri += 1

        else:
            anticipate_vaule = base_values[vi] * rules[ri]['percent']

        anticipate_vaules[vi] = int(anticipate_vaule)
        vi += 1

print(anticipate_vaules)

# EOF
