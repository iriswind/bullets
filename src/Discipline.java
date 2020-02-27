public class Discipline {
    public Integer id;
    public String  discp_group;
    public Integer age_low;
    public Integer age_high;
    public Integer weight;
    public String full_name;

    public Discipline(Integer id, String discp_group,Integer age_low,Integer age_high,Integer weight, String full_name){
        this.id=id;
        this.discp_group=discp_group;
        this.weight=weight;
        this.age_low=age_low;
        this.age_high=age_high;
        this.full_name=full_name;
    }
}
