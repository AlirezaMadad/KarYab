using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using KarYab.Models;

namespace KarYab.Controllers
{
    public class HUMenController : Controller
    {
        private KARYABDBEntities db = new KARYABDBEntities();

        // GET: HUMen
        public ActionResult Index()
        {
            return View(db.HUMen.ToList());
        }

        // GET: HUMen/Details/5
        public ActionResult Details(long? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            HUMAN hUMAN = db.HUMen.Find(id);
            if (hUMAN == null)
            {
                return HttpNotFound();
            }
            return View(hUMAN);
        }

        // GET: HUMen/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: HUMen/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "ID,NAME,GENDER,USAGE")] HUMAN hUMAN)
        {
            if (ModelState.IsValid)
            {
                db.HUMen.Add(hUMAN);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(hUMAN);
        }

        // GET: HUMen/Edit/5
        public ActionResult Edit(long? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            HUMAN hUMAN = db.HUMen.Find(id);
            if (hUMAN == null)
            {
                return HttpNotFound();
            }
            return View(hUMAN);
        }

        // POST: HUMen/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "ID,NAME,GENDER,USAGE")] HUMAN hUMAN)
        {
            if (ModelState.IsValid)
            {
                db.Entry(hUMAN).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(hUMAN);
        }

        // GET: HUMen/Delete/5
        public ActionResult Delete(long? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            HUMAN hUMAN = db.HUMen.Find(id);
            if (hUMAN == null)
            {
                return HttpNotFound();
            }
            return View(hUMAN);
        }

        // POST: HUMen/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(long id)
        {
            HUMAN hUMAN = db.HUMen.Find(id);
            db.HUMen.Remove(hUMAN);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
