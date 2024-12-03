package despairscent.skyblockm.tweaks.modules.compactgenome;

import despairscent.skyblockm.tweaks.ModUtils;

import java.util.Map;

public class GenomeType<V extends GenomeValue> {

    final String name;
    final V[] values;
    final Map<String, V> valuesConverter;

    GenomeType(String name, V[] values) {
        this.name = name;
        this.values = values;
        this.valuesConverter = ModUtils.generateConvertMap(values, GenomeValue::getValue);
    }

    String getName() {
        return this.name;
    }

    V parseValue(String valueStr) {
        return this.valuesConverter.get(valueStr);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
