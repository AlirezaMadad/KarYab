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
    public class CellPhoneApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
         public long SetCellPhone(CELLPHONE CellPhone, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    db.CELLPHONES.Add(CellPhone);
                    db.SaveChanges();
                    OWNERSCELLPHONE owenersCellPhone = new OWNERSCELLPHONE();
                    owenersCellPhone.CELLPHONEID = CellPhone.ID;
                    owenersCellPhone.CELLPHONEOWNERID = HumanID;
                    db.OWNERSCELLPHONES.Add(owenersCellPhone);
                    db.SaveChanges();
                    return CellPhone.ID;
                }
                catch
                {
                    return 0;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public CELLPHONE GetCellPhone(long ID, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    if (db.OWNERSCELLPHONES.Any(r => r.CELLPHONEOWNERID.Equals(HumanID) && r.CELLPHONEID.Equals(ID)))
                    {
                        return db.CELLPHONES.Where(r => r.ID.Equals(ID)).Single();
                    }
                    else
                    {
                        return new CELLPHONE();
                    }

                }
                catch
                {
                    return new CELLPHONE();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdateCellPhone(CELLPHONE Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.CELLPHONES.Attach(Entity);
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
        public bool DeleteCellPhone(CELLPHONE Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    CELLPHONE entity = db.CELLPHONES.FirstOrDefault(q => q.ID == Entity.ID);
                    OWNERSCELLPHONE Owner = db.OWNERSCELLPHONES.FirstOrDefault(q => q.CELLPHONEID == Entity.ID);
                    db.OWNERSCELLPHONES.Attach(Owner);
                    db.OWNERSCELLPHONES.Remove(Owner);
                    db.SaveChanges();
                    db.CELLPHONES.Attach(entity);
                    db.CELLPHONES.Remove(entity);
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
        public List<CELLPHONE> GetHumanCellPhones(long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    List<long> cellPhoneIds = new List<long>();
                    cellPhoneIds = db.OWNERSCELLPHONES.Where(r => r.CELLPHONEOWNERID.Equals(HumanID)).Select(r => r.CELLPHONEID).ToList();
                    List<CELLPHONE> cellPhones = new List<CELLPHONE>();
                    foreach (long item in cellPhoneIds)
                    {
                        var cellPhone = db.CELLPHONES.Where(r => r.ID.Equals(item)).SingleOrDefault();
                        cellPhones.Add(cellPhone);
                    }
                    return cellPhones;
                }
                catch (Exception e)
                {
                    return new List<CELLPHONE>();
                }
            }
        }
    }
}
