
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using KarYab.Models;
namespace KarYab.Controllers
{
    public class PasswordApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetPassword(PASSWORD Password)//long HumanID ,string password,string confirmPassword,string email ,string answer)
        {
            //PASSWORD Password = new PASSWORD();
            //Password.ANSWER = answer;
            //Password.CONFIRMPASSWORD = confirmPassword;
            //Password.PASSWORD1 = password;
            //Password.EMAIL = email;
            //Password.HUMANID = HumanID;

            try
            {
                using (KARYABDBEntities db = new KARYABDBEntities())
                {
                    if(Password.PASSWORD1.Equals(Password.CONFIRMPASSWORD) 
                        && Password.ANSWER !=null 
                        && Password.EMAIL != null 
                        && Password.HUMANID != null)
                    {
                        var EmailExist = db.PASSWORDS.Where(q => q.EMAIL == Password.EMAIL).ToList();
                        if(EmailExist.Any())
                        {
                            if (EmailExist.Any(q => q.ANSWER == Password.ANSWER))
                            {
                                return -2;
                            }
                            else
                            {
                                return -1;
                            }
                        }
                        else
                        {
                            db.PASSWORDS.Add(Password);
                            db.SaveChanges();
                            return Password.ID;

                        }
                         
                    }
                    else
                    {
                        return 0;
                    }
                   
                }
            }
            catch (Exception e)
            {
                return 0;
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long Login(string Email, string Password)
        {
            try
            {
                using (KARYABDBEntities db = new KARYABDBEntities())
                {
                    var EmailExist = db.PASSWORDS.Where(q => q.EMAIL.Equals(Email) && q.PASSWORD1.Equals(Password)).ToList();
                    if (EmailExist.Any() && EmailExist.Count() == 1)
                    {
                        var account = EmailExist.FirstOrDefault();
                        if (account.PASSWORD1.Equals(account.CONFIRMPASSWORD))
                        {
                            if (account.HUMANID != null && account.ANSWER != null)
                            {
                                return account.HUMANID;
                            }
                            else
                            {
                                return -3;
                            }
                        }
                        else
                        {
                            return -2;
                        }
                    }
                    else
                    {
                        return -1;
                    }
                }
               
            }
            catch (Exception e)
            {
                return 0;
            }

        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public string GetPassword(string Email,string Answer)
        {
            try
            {
                using (KARYABDBEntities db = new KARYABDBEntities())
                {
                    var Password = db.PASSWORDS.Where(q => q.EMAIL.Equals(Email) && q.ANSWER.Equals(Answer)).ToList();
                    if (Password.Any() && Password.Count == 1)
                    {
                        var password = Password.FirstOrDefault();
                        if (password.PASSWORD1 != null && password.HUMANID != null && password.CONFIRMPASSWORD != null && password.PASSWORD1.Equals(password.CONFIRMPASSWORD))
                        {
                            return password.PASSWORD1;
                        }
                        else
                        {
                            return "-2";
                        }
                    }
                    else
                    {
                        return "-1";
                    }
                }
            }
            catch (Exception e)
            {
                return "0";
            }
        }
    }
}
