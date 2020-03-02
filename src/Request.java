public class Request {
    public Integer id;
    public Integer id_comp;
    public String fio;
    public String club;
    public String r_date;
    public String level;
    public Integer weight;
    public String kata;
    public String kumite;
    public String trainer;
    public Request(Integer id, Integer id_comp,String fio, String club, String r_date, String level, Integer weight, String kata, String kumite, String trainer)
    {
        this.id=id;
        this.id_comp=id_comp;
        this.fio=fio;
        this.club=club;
        this.r_date=r_date;
        this.level=level;
        this.weight=weight;
        this.kata=kata;
        this.kumite=kumite;
        this.trainer=trainer;
    }

}