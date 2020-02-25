public class Discipline {
    public Integer id;
    public String  discp_group;
    public Integer age_low;
    public Integer age_high;
    public Integer weight;

    public Discipline(Integer id, String discp_group,Integer age_low,Integer age_high,Integer weight){
        this.id=id;
        this.discp_group=discp_group;
        this.weight=weight;
        this.age_low=age_low;
        this.age_high=age_high;
    }
    @Override
    public String toString() {
        String str = this.id + " | " + this.discp_group;
        if (this.age_low == this.age_high) {
            str = str + " | " + this.age_low.toString() + "лет";
            }
        else {
            str = str + " | " + this.age_low.toString() + "-" + this.age_high.toString() + "лет";
            }
        if (this.discp_group.toLowerCase().equals("кумитэ"))
            {
            str=str+" | "+this.weight.toString()+"кг";
            }
        return str;
    }
}
