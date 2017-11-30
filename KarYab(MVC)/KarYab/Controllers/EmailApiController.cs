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
    public class EmailApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetEmail(MAILADRESS Adress, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    db.MAILADRESSES.Add(Adress);
                    db.SaveChanges();
                    OWNERSMAIL owenersAdress = new OWNERSMAIL();
                    owenersAdress.MAILADRESSID = Adress.ID;
                    owenersAdress.MAILOWNERID = HumanID;
                    db.OWNERSMAILS.Add(owenersAdress);
                    db.SaveChanges();
                    return Adress.ID;
                }
                catch
                {
                    return 0;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public MAILADRESS GetEmail(long ID, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    if (db.OWNERSMAILS.Any(r => r.MAILOWNERID.Equals(HumanID) && r.MAILADRESSID.Equals(ID)))
                    {
                        return db.MAILADRESSES.Where(r => r.ID.Equals(ID)).Single();
                    }
                    else
                    {
                        return new MAILADRESS();
                    }

                }
                catch
                {
                    return new MAILADRESS();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdateEmail(MAILADRESS Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.MAILADRESSES.Attach(Entity);
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
        public bool DeleteEmail(MAILADRESS Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    MAILADRESS entity = db.MAILADRESSES.FirstOrDefault(q => q.ID == Entity.ID);
                    OWNERSMAIL Owner = db.OWNERSMAILS.FirstOrDefault(q => q.MAILADRESSID == Entity.ID);
                    db.OWNERSMAILS.Attach(Owner);
                    db.OWNERSMAILS.Remove(Owner);
                    db.SaveChanges();
                    db.MAILADRESSES.Attach(entity);
                    db.MAILADRESSES.Remove(entity);
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
        public List<MAILADRESS> GetHumanEmails(long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    List<long> AdressIds = new List<long>();
                    AdressIds = db.OWNERSMAILS.Where(r => r.MAILOWNERID.Equals(HumanID)).Select(r => r.MAILADRESSID).ToList();
                    List<MAILADRESS> adresses = new List<MAILADRESS>();
                    foreach (long item in AdressIds)
                    {
                        var adress = db.MAILADRESSES.Where(r => r.ID.Equals(item)).SingleOrDefault();
                        adresses.Add(adress);
                    }
                    return adresses;
                }
                catch (Exception e)
                {
                    return new List<MAILADRESS>();
                }
            }
        }
    }
}
