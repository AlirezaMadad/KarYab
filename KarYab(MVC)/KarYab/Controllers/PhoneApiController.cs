using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using KarYab.Models;
using System.Data.Entity;
namespace KarYab.Controllers
{
    public class PhoneApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetPhone(PHONE Phone, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    db.PHONES.Add(Phone);
                    db.SaveChanges();
                    OWNERSPHO owenersPhone = new OWNERSPHO();
                    owenersPhone.PHONENUMBERID = Phone.ID;
                    owenersPhone.PHONEOWNERID = HumanID;
                    db.OWNERSPHOES.Add(owenersPhone);
                    db.SaveChanges();
                    return Phone.ID;
                }
                catch
                {
                    return 0;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public PHONE GetPhone(long ID, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    if (db.OWNERSPHOES.Any(r => r.PHONEOWNERID.Equals(HumanID) && r.PHONENUMBERID.Equals(ID)))
                    {
                        return db.PHONES.Where(r => r.ID.Equals(ID)).Single();
                    }
                    else
                    {
                        return new PHONE();
                    }

                }
                catch
                {
                    return new PHONE();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdatePhone(PHONE Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.PHONES.Attach(Entity);
                    var Entry = db.Entry(Entity);
                    Entry.State = EntityState.Modified;
                    db.SaveChanges();
                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool DeletePhone(PHONE Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    PHONE entity = db.PHONES.FirstOrDefault(q => q.ID == Entity.ID);
                    OWNERSPHO Owner = db.OWNERSPHOES.FirstOrDefault(q => q.PHONENUMBERID == Entity.ID);
                    db.OWNERSPHOES.Attach(Owner);
                    db.OWNERSPHOES.Remove(Owner);
                    db.SaveChanges();
                    db.PHONES.Attach(entity);
                    db.PHONES.Remove(entity);
                    db.SaveChanges();

                    return true;
                }
                catch (Exception e)
                {
                    return false;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public List<PHONE> GetHumanPhones(long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    List<long> phoneIds = new List<long>();
                    phoneIds = db.OWNERSPHOES.Where(r => r.PHONEOWNERID.Equals(HumanID)).Select(r => r.PHONENUMBERID).ToList();
                    List<PHONE> phones = new List<PHONE>();
                    foreach (long item in phoneIds)
                    {
                        var phone = db.PHONES.Where(r => r.ID.Equals(item)).SingleOrDefault();
                        phones.Add(phone);
                    }
                    return phones;
                }
                catch (Exception e)
                {
                    return new List<PHONE>();
                }
            }
        }
    }
}
