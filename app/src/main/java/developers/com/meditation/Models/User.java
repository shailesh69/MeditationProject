package developers.com.meditation.Models;

/**
 * Created by user on 2/13/2018.
 */

public class User {

    private String name ;
    private String email ;
    private String password ;
    private String DOB ;
    private String gender ;

    public void setName(String name){
        this.name = name ;
    }
    public String getName(){
        return name;
    }
    public void setEmail(String email){
        this.email = email ;
    }
    public String getEmail(){
        return email ;
    }
    public void setPassword(String password){
        this.password = password ;
    }
    public String getPassword(){
        return password ;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }
    public void setDOB(String DOB) {
        this.DOB = DOB ;
    }
    public String getDOB() {
        return DOB ;
    }
    public User(User user){
        this.name = user.getName() ;
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.DOB = user.getDOB() ;
        this.gender = user.getGender() ;
    }
    public User(){

    }
}
