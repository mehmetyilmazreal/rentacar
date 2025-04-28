import os
import re

JAVA_DIR = "src"  # Java dosyalarının olduğu klasör

def parse_java_file(filepath):
    with open(filepath, encoding="utf-8") as f:
        content = f.read()

    # Sınıf, enum veya interface bul
    class_match = re.search(r'(public\s+)?(class|enum|interface)\s+(\w+)', content)
    if not class_match:
        return None

    class_type = class_match.group(2)
    class_name = class_match.group(3)
    fields = []
    methods = []
    field_types = []

    # Alanları bul (private/public/protected tip isim;)
    for field in re.findall(r'(private|public|protected)\s+([\w<>\[\]]+)\s+(\w+)\s*;', content):
        vis, typ, name = field
        fields.append(f"{vis} {typ} {name}")
        # Sadece ana tipleri al (List<Car> -> Car, int[] -> int)
        main_type = re.sub(r'List<(\w+)>', r'\1', typ)
        main_type = re.sub(r'ArrayList<(\w+)>', r'\1', main_type)
        main_type = re.sub(r'[\[\]]', '', main_type)
        field_types.append(main_type)

    # Metotları bul (public/private/protected tip isim(parametreler)
    for method in re.findall(r'(public|private|protected)\s+([\w<>\[\]]+)\s+(\w+)\s*\(([^)]*)\)', content):
        vis, ret, name, params = method
        methods.append(f"{vis} {ret} {name}({params})")

    return {
        "name": class_name,
        "type": class_type,
        "fields": fields,
        "methods": methods,
        "field_types": field_types
    }

def main():
    classes = []
    class_names = set()
    for fname in os.listdir(JAVA_DIR):
        if fname.endswith(".java"):
            parsed = parse_java_file(os.path.join(JAVA_DIR, fname))
            if parsed:
                classes.append(parsed)
                class_names.add(parsed["name"])

    # PlantUML çıktısı
    print("@startuml")
    for c in classes:
        stereotype = "class"
        if c["type"] == "enum":
            stereotype = "enum"
        elif c["type"] == "interface":
            stereotype = "interface"
        print(f"{stereotype} {c['name']} {{")
        for f in c["fields"]:
            print(f"  {f}")
        for m in c["methods"]:
            print(f"  {m}")
        print("}")

    # İlişkiler (has-a)
    for c in classes:
        for t in c["field_types"]:
            t_clean = t.replace("[]", "")
            if t_clean in class_names and t_clean != c["name"]:
                print(f"{c['name']} --> {t_clean}")

    print("@enduml")

if __name__ == "__main__":
    main()
