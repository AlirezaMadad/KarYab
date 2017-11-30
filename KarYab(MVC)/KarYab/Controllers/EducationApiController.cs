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
    public class EducationApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetEducation(EDUCATON Education, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    //long HumanID = 60111;
                    //EDUCATON Education = new EDUCATON();
                    //Education.EDUCATIONBRANCH = "asd";
                    //Education.EDUCATIONDIPLOMA = "asd";
                    //Education.EDUCATIONDURATION = 12;
                    //Education.EDUCATIONLOCATION = "asd";
                    //Education.FINALGRADE = 12;
                    db.Configuration.ProxyCreationEnabled = false;
                    db.EDUCATONS.Add(Education);
                    db.SaveChanges();
                    OWNERSEDUCATION owenersEducation = new OWNERSEDUCATION();
                    owenersEducation.EDUCATIONID = Education.ID;
                    owenersEducation.EDUCATIONOWNERID = HumanID;
                    db.OWNERSEDUCATIONS.Add(owenersEducation);
                    db.SaveChanges();
                    return Education.ID;
                }
                catch
                {
                    return 0;
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public EDUCATON GetEducation(long ID, long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    if (db.OWNERSEDUCATIONS.Any(r => r.EDUCATIONOWNERID.Equals(HumanID) && r.EDUCATIONID.Equals(ID)))
                    {
                        return db.EDUCATONS.Where(r => r.ID.Equals(ID)).Single();
                    }
                    else
                    {
                        return new EDUCATON();
                    }

                }
                catch
                {
                    return new EDUCATON();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdateEducation(EDUCATON Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.EDUCATONS.Attach(Entity);
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
        public bool DeleteEducation(EDUCATON Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    EDUCATON entity = db.EDUCATONS.FirstOrDefault(q => q.ID == Entity.ID);
                    OWNERSEDUCATION Owner = db.OWNERSEDUCATIONS.FirstOrDefault(q => q.EDUCATIONID == Entity.ID);
                    db.OWNERSEDUCATIONS.Attach(Owner);
                    db.OWNERSEDUCATIONS.Remove(Owner);
                    db.SaveChanges();
                    db.EDUCATONS.Attach(entity);
                    db.EDUCATONS.Remove(entity);
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
        public List<EDUCATON> GetHumanEducations(long HumanID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    List<long> educationIds = new List<long>();
                    educationIds = db.OWNERSEDUCATIONS.Where(r => r.EDUCATIONOWNERID.Equals(HumanID)).Select(r => r.EDUCATIONID).ToList();
                    List<EDUCATON> educations = new List<EDUCATON>();
                    foreach (long item in educationIds)
                    {
                        var education = db.EDUCATONS.Where(r => r.ID.Equals(item)).SingleOrDefault();
                        educations.Add(education);
                    }
                    return educations;
                }
                catch (Exception e)
                {
                    return new List<EDUCATON>();
                }
            }
        }
    }
}
