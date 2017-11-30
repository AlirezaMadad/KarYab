using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using KarYab.Models;
using System.Web.Mvc;
using System.Data.Entity;
namespace KarYab.Controllers
{
    public class HomeApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetHuman(HUMAN human)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    db.HUMen.Add(human);
                    db.SaveChanges();

                    return human.ID;
                }
                catch
                {
                    return 0;
                }
            }
        }

        public HUMAN GetHuman(long ID)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    db.Configuration.ProxyCreationEnabled = false;
                    return db.HUMen.Where(r => r.ID.Equals(ID)).Single();
                }
                catch
                {
                    return new HUMAN();
                }
            }
        }
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public bool UpdateHuman(HUMAN Entity)
        {
            using (KARYABDBEntities db = new KARYABDBEntities())
            {
                try
                {
                    // var Skill = db.SKILLS.FirstOrDefault(q => q.ID == SkillID);
                    db.HUMen.Attach(Entity);
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
        //[System.Web.Http.AcceptVerbs("GET", "POST")]
        //[System.Web.Http.HttpGet]
        //public bool DeleteHuman(HUMAN Entity)
        //{
        //    using (KARYABDBEntities db = new KARYABDBEntities())
        //    {
        //        try
        //        {
        //            db.Configuration.ProxyCreationEnabled = false;
        //            HUMAN entity = db.EXPERIENCES.FirstOrDefault(q => q.ID == Entity.ID);
        //            OWNERSEXPERIENCE Owner = db.OWNERSEXPERIENCES.FirstOrDefault(q => q.EXPERIENCEID == Entity.ID);
        //            db.OWNERSEXPERIENCES.Attach(Owner);
        //            db.OWNERSEXPERIENCES.Remove(Owner);
        //            db.SaveChanges();
        //            db.EXPERIENCES.Attach(entity);
        //            db.EXPERIENCES.Remove(entity);
        //            db.SaveChanges();

        //            return true;
        //        }
        //        catch (Exception e)
        //        {
        //            return false;
        //        }
        //    }
        //}
    }
}
