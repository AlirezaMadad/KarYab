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
    public class AdressApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetAdress(ADRESS Adress, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    db.ADRESSES.Add(Adress);
                    db.SaveChanges();
                    OWNERSADRES owenersAdress = new OWNERSADRES();
                    owenersAdress.ADRESSID = Adress.ID;
                    owenersAdress.ADRESSOWNERID = HumanID;
                    db.OWNERSADRESES.Add(owenersAdress);
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
        public ADRESS GetAdress(long ID, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    if (db.OWNERSADRESES.Any(r => r.ADRESSOWNERID.Equals(HumanID) && r.ADRESSID.Equals(ID)))
                    {
                        return db.ADRESSES.Where(r => r.ID.Equals(ID)).Single();
                    }
                    else
                    {
                        return new ADRESS();
                    }

                }
                catch
                {
                    return new ADRESS();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdateAdress(ADRESS Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.ADRESSES.Attach(Entity);
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
        public bool DeleteAdress(ADRESS Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    ADRESS entity = db.ADRESSES.FirstOrDefault(q => q.ID == Entity.ID);
                    OWNERSADRES Owner = db.OWNERSADRESES.FirstOrDefault(q => q.ADRESSID == Entity.ID);
                    db.OWNERSADRESES.Attach(Owner);
                    db.OWNERSADRESES.Remove(Owner);
                    db.SaveChanges();
                    db.ADRESSES.Attach(entity);
                    db.ADRESSES.Remove(entity);
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
        public List<ADRESS> GetHumanAdresses(long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    List<long> AdressIds = new List<long>();
                    AdressIds = db.OWNERSADRESES.Where(r => r.ADRESSOWNERID.Equals(HumanID)).Select(r => r.ADRESSID).ToList();
                    List<ADRESS> adresses = new List<ADRESS>();
                    foreach (long item in AdressIds)
                    {
                        var adress = db.ADRESSES.Where(r => r.ID.Equals(item)).SingleOrDefault();
                        adresses.Add(adress);
                    }
                    return adresses;
                }
                catch (Exception e)
                {
                    return new List<ADRESS>();
                }
            }
        }
    }
}
